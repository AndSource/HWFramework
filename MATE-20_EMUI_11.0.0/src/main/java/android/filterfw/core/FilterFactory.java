package android.filterfw.core;

import android.util.Log;
import dalvik.system.PathClassLoader;
import java.util.HashSet;
import java.util.Iterator;

public class FilterFactory {
    private static final String TAG = "FilterFactory";
    private static Object mClassLoaderGuard = new Object();
    private static ClassLoader mCurrentClassLoader = Thread.currentThread().getContextClassLoader();
    private static HashSet<String> mLibraries = new HashSet<>();
    private static boolean mLogVerbose = Log.isLoggable(TAG, 2);
    private static FilterFactory mSharedFactory;
    private HashSet<String> mPackages = new HashSet<>();

    public static FilterFactory sharedFactory() {
        if (mSharedFactory == null) {
            mSharedFactory = new FilterFactory();
        }
        return mSharedFactory;
    }

    public static void addFilterLibrary(String libraryPath) {
        if (mLogVerbose) {
            Log.v(TAG, "Adding filter library " + libraryPath);
        }
        synchronized (mClassLoaderGuard) {
            if (mLibraries.contains(libraryPath)) {
                if (mLogVerbose) {
                    Log.v(TAG, "Library already added");
                }
                return;
            }
            mLibraries.add(libraryPath);
            mCurrentClassLoader = new PathClassLoader(libraryPath, mCurrentClassLoader);
        }
    }

    public void addPackage(String packageName) {
        if (mLogVerbose) {
            Log.v(TAG, "Adding package " + packageName);
        }
        this.mPackages.add(packageName);
    }

    public Filter createFilterByClassName(String className, String filterName) {
        if (mLogVerbose) {
            Log.v(TAG, "Looking up class " + className);
        }
        Class filterClass = null;
        Iterator<String> it = this.mPackages.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String packageName = it.next();
            try {
                if (mLogVerbose) {
                    Log.v(TAG, "Trying " + packageName + "." + className);
                }
                synchronized (mClassLoaderGuard) {
                    ClassLoader classLoader = mCurrentClassLoader;
                    filterClass = classLoader.loadClass(packageName + "." + className);
                }
                if (filterClass != null) {
                    break;
                }
            } catch (ClassNotFoundException e) {
            }
        }
        if (filterClass != null) {
            return createFilterByClass(filterClass, filterName);
        }
        throw new IllegalArgumentException("Unknown filter class '" + className + "'!");
    }

    public Filter createFilterByClass(Class filterClass, String filterName) {
        try {
            filterClass.asSubclass(Filter.class);
            try {
                Filter filter = null;
                try {
                    filter = (Filter) filterClass.getConstructor(String.class).newInstance(filterName);
                } catch (Throwable th) {
                }
                if (filter != null) {
                    return filter;
                }
                throw new IllegalArgumentException("Could not construct the filter '" + filterName + "'!");
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("The filter class '" + filterClass + "' does not have a constructor of the form <init>(String name)!");
            }
        } catch (ClassCastException e2) {
            throw new IllegalArgumentException("Attempting to allocate class '" + filterClass + "' which is not a subclass of Filter!");
        }
    }
}
