package com.huawei.zxing.multi;

import com.huawei.zxing.BinaryBitmap;
import com.huawei.zxing.DecodeHintType;
import com.huawei.zxing.NotFoundException;
import com.huawei.zxing.Reader;
import com.huawei.zxing.ReaderException;
import com.huawei.zxing.Result;
import com.huawei.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader delegate2) {
        this.delegate = delegate2;
    }

    @Override // com.huawei.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap image) throws NotFoundException {
        return decodeMultiple(image, null);
    }

    @Override // com.huawei.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException {
        List<Result> results = new ArrayList<>();
        doDecodeMultiple(image, hints, results, 0, 0, 0);
        if (!results.isEmpty()) {
            return (Result[]) results.toArray(new Result[results.size()]);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void doDecodeMultiple(BinaryBitmap image, Map<DecodeHintType, ?> hints, List<Result> results, int xOffset, int yOffset, int currentDepth) {
        boolean alreadyFound;
        int height;
        float minY;
        float maxX;
        int width;
        if (currentDepth <= 4) {
            try {
                Result result = this.delegate.decode(image, hints);
                Iterator<Result> it = results.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().getText().equals(result.getText())) {
                            alreadyFound = true;
                            break;
                        }
                    } else {
                        alreadyFound = false;
                        break;
                    }
                }
                if (!alreadyFound) {
                    results.add(translateResultPoints(result, xOffset, yOffset));
                }
                ResultPoint[] resultPoints = result.getResultPoints();
                if (resultPoints == null) {
                    return;
                }
                if (resultPoints.length != 0) {
                    int width2 = image.getWidth();
                    int height2 = image.getHeight();
                    float maxY = 0.0f;
                    float minX = (float) width2;
                    float minY2 = (float) height2;
                    float maxX2 = 0.0f;
                    for (ResultPoint point : resultPoints) {
                        float x = point.getX();
                        float y = point.getY();
                        if (x < minX) {
                            minX = x;
                        }
                        if (y < minY2) {
                            minY2 = y;
                        }
                        if (x > maxX2) {
                            maxX2 = x;
                        }
                        if (y > maxY) {
                            maxY = y;
                        }
                    }
                    if (minX > 100.0f) {
                        maxX = maxX2;
                        minY = minY2;
                        height = height2;
                        width = width2;
                        doDecodeMultiple(image.crop(0, 0, (int) minX, height2), hints, results, xOffset, yOffset, currentDepth + 1);
                    } else {
                        maxX = maxX2;
                        height = height2;
                        width = width2;
                        minY = minY2;
                    }
                    if (minY > 100.0f) {
                        doDecodeMultiple(image.crop(0, 0, width, (int) minY), hints, results, xOffset, yOffset, currentDepth + 1);
                    }
                    if (maxX < ((float) (width - 100))) {
                        doDecodeMultiple(image.crop((int) maxX, 0, width - ((int) maxX), height), hints, results, xOffset + ((int) maxX), yOffset, currentDepth + 1);
                    }
                    if (maxY < ((float) (height - 100))) {
                        doDecodeMultiple(image.crop(0, (int) maxY, width, height - ((int) maxY)), hints, results, xOffset, yOffset + ((int) maxY), currentDepth + 1);
                    }
                }
            } catch (ReaderException e) {
            }
        }
    }

    private static Result translateResultPoints(Result result, int xOffset, int yOffset) {
        ResultPoint[] oldResultPoints = result.getResultPoints();
        if (oldResultPoints == null) {
            return result;
        }
        ResultPoint[] newResultPoints = new ResultPoint[oldResultPoints.length];
        for (int i = 0; i < oldResultPoints.length; i++) {
            ResultPoint oldPoint = oldResultPoints[i];
            newResultPoints[i] = new ResultPoint(oldPoint.getX() + ((float) xOffset), oldPoint.getY() + ((float) yOffset));
        }
        Result newResult = new Result(result.getText(), result.getRawBytes(), newResultPoints, result.getBarcodeFormat());
        newResult.putAllMetadata(result.getResultMetadata());
        return newResult;
    }
}
