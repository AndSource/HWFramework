package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat256;

public class SecP256R1Point extends ECPoint.AbstractFp {
    public SecP256R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y) {
        this(curve, x, y, false);
    }

    public SecP256R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression) {
        super(curve, x, y);
        boolean z = false;
        if ((x == null) == (y == null ? true : z)) {
            this.withCompression = withCompression;
            return;
        }
        throw new IllegalArgumentException("Exactly one of the field elements is null");
    }

    SecP256R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression) {
        super(curve, x, y, zs);
        this.withCompression = withCompression;
    }

    /* access modifiers changed from: protected */
    public ECPoint detach() {
        return new SecP256R1Point(null, getAffineXCoord(), getAffineYCoord());
    }

    public ECPoint add(ECPoint b) {
        int[] S2;
        int[] U2;
        int[] S1;
        int[] U1;
        SecP256R1FieldElement Y3;
        ECPoint eCPoint = b;
        if (isInfinity()) {
            return eCPoint;
        }
        if (b.isInfinity()) {
            return this;
        }
        if (this == eCPoint) {
            return twice();
        }
        ECCurve curve = getCurve();
        SecP256R1FieldElement X1 = (SecP256R1FieldElement) this.x;
        SecP256R1FieldElement Y1 = (SecP256R1FieldElement) this.y;
        SecP256R1FieldElement X2 = (SecP256R1FieldElement) b.getXCoord();
        SecP256R1FieldElement Y2 = (SecP256R1FieldElement) b.getYCoord();
        SecP256R1FieldElement Z1 = (SecP256R1FieldElement) this.zs[0];
        SecP256R1FieldElement Z2 = (SecP256R1FieldElement) eCPoint.getZCoord(0);
        int[] tt1 = Nat256.createExt();
        int[] t2 = Nat256.create();
        int[] t3 = Nat256.create();
        int[] t4 = Nat256.create();
        boolean Z1IsOne = Z1.isOne();
        if (Z1IsOne) {
            U2 = X2.x;
            S2 = Y2.x;
        } else {
            S2 = t3;
            SecP256R1Field.square(Z1.x, S2);
            U2 = t2;
            SecP256R1Field.multiply(S2, X2.x, U2);
            SecP256R1Field.multiply(S2, Z1.x, S2);
            SecP256R1Field.multiply(S2, Y2.x, S2);
        }
        int[] U22 = U2;
        boolean Z2IsOne = Z2.isOne();
        if (Z2IsOne) {
            U1 = X1.x;
            S1 = Y1.x;
        } else {
            S1 = t4;
            SecP256R1Field.square(Z2.x, S1);
            U1 = tt1;
            SecP256R1Field.multiply(S1, X1.x, U1);
            SecP256R1Field.multiply(S1, Z2.x, S1);
            SecP256R1Field.multiply(S1, Y1.x, S1);
        }
        int[] U12 = U1;
        int[] S12 = S1;
        int[] S13 = Nat256.create();
        SecP256R1Field.subtract(U12, U22, S13);
        int[] R = t2;
        SecP256R1Field.subtract(S12, S2, R);
        if (!Nat256.isZero(S13)) {
            SecP256R1FieldElement secP256R1FieldElement = X1;
            int[] HSquared = t3;
            SecP256R1Field.square(S13, HSquared);
            SecP256R1FieldElement secP256R1FieldElement2 = Y1;
            int[] G = Nat256.create();
            SecP256R1Field.multiply(HSquared, S13, G);
            SecP256R1FieldElement secP256R1FieldElement3 = X2;
            int[] V = t3;
            SecP256R1Field.multiply(HSquared, U12, V);
            SecP256R1Field.negate(G, G);
            Nat256.mul(S12, G, tt1);
            int[] iArr = HSquared;
            SecP256R1Field.reduce32(Nat256.addBothTo(V, V, G), G);
            int[] S14 = S12;
            SecP256R1FieldElement X3 = new SecP256R1FieldElement(t4);
            int[] U13 = U12;
            SecP256R1Field.square(R, X3.x);
            int[] S22 = S2;
            SecP256R1Field.subtract(X3.x, G, X3.x);
            SecP256R1FieldElement Y32 = new SecP256R1FieldElement(G);
            SecP256R1FieldElement X32 = X3;
            SecP256R1Field.subtract(V, X3.x, Y32.x);
            SecP256R1Field.multiplyAddToExt(Y32.x, R, tt1);
            SecP256R1Field.reduce(tt1, Y32.x);
            SecP256R1FieldElement Z3 = new SecP256R1FieldElement(S13);
            if (!Z1IsOne) {
                int[] iArr2 = R;
                Y3 = Y32;
                SecP256R1Field.multiply(Z3.x, Z1.x, Z3.x);
            } else {
                Y3 = Y32;
            }
            if (!Z2IsOne) {
                SecP256R1Field.multiply(Z3.x, Z2.x, Z3.x);
            }
            ECFieldElement[] zs = {Z3};
            int[] iArr3 = S13;
            int[] iArr4 = S14;
            SecP256R1FieldElement X33 = X32;
            SecP256R1FieldElement secP256R1FieldElement4 = Z3;
            int[] iArr5 = U13;
            int[] iArr6 = S22;
            SecP256R1FieldElement Y33 = Y3;
            SecP256R1FieldElement secP256R1FieldElement5 = X33;
            int[] iArr7 = U22;
            SecP256R1FieldElement secP256R1FieldElement6 = Y33;
            int[] iArr8 = t4;
            SecP256R1Point secP256R1Point = new SecP256R1Point(curve, secP256R1FieldElement5, secP256R1FieldElement6, zs, this.withCompression);
            return secP256R1Point;
        } else if (Nat256.isZero(R)) {
            return twice();
        } else {
            return curve.getInfinity();
        }
    }

    public ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        ECCurve curve = getCurve();
        SecP256R1FieldElement Y1 = (SecP256R1FieldElement) this.y;
        if (Y1.isZero()) {
            return curve.getInfinity();
        }
        SecP256R1FieldElement X1 = (SecP256R1FieldElement) this.x;
        SecP256R1FieldElement Z1 = (SecP256R1FieldElement) this.zs[0];
        int[] t1 = Nat256.create();
        int[] t2 = Nat256.create();
        int[] Y1Squared = Nat256.create();
        SecP256R1Field.square(Y1.x, Y1Squared);
        int[] T = Nat256.create();
        SecP256R1Field.square(Y1Squared, T);
        boolean Z1IsOne = Z1.isOne();
        int[] Z1Squared = Z1.x;
        if (!Z1IsOne) {
            Z1Squared = t2;
            SecP256R1Field.square(Z1.x, Z1Squared);
        }
        int[] Z1Squared2 = Z1Squared;
        SecP256R1Field.subtract(X1.x, Z1Squared2, t1);
        int[] M = t2;
        SecP256R1Field.add(X1.x, Z1Squared2, M);
        SecP256R1Field.multiply(M, t1, M);
        int c = Nat256.addBothTo(M, M, M);
        SecP256R1Field.reduce32(c, M);
        int[] S = Y1Squared;
        SecP256R1Field.multiply(Y1Squared, X1.x, S);
        int i = c;
        int c2 = Nat.shiftUpBits(8, S, 2, 0);
        SecP256R1Field.reduce32(c2, S);
        int i2 = c2;
        int c3 = Nat.shiftUpBits(8, T, 3, 0, t1);
        SecP256R1Field.reduce32(c3, t1);
        SecP256R1FieldElement X3 = new SecP256R1FieldElement(T);
        SecP256R1Field.square(M, X3.x);
        int i3 = c3;
        SecP256R1Field.subtract(X3.x, S, X3.x);
        SecP256R1Field.subtract(X3.x, S, X3.x);
        SecP256R1FieldElement Y3 = new SecP256R1FieldElement(S);
        SecP256R1FieldElement X32 = X3;
        SecP256R1Field.subtract(S, X3.x, Y3.x);
        SecP256R1Field.multiply(Y3.x, M, Y3.x);
        SecP256R1Field.subtract(Y3.x, t1, Y3.x);
        SecP256R1FieldElement Z3 = new SecP256R1FieldElement(M);
        SecP256R1FieldElement Y32 = Y3;
        SecP256R1Field.twice(Y1.x, Z3.x);
        if (!Z1IsOne) {
            int[] iArr = S;
            SecP256R1Field.multiply(Z3.x, Z1.x, Z3.x);
        }
        SecP256R1FieldElement secP256R1FieldElement = Z3;
        int[] iArr2 = M;
        ECFieldElement[] eCFieldElementArr = {Z3};
        int[] iArr3 = Z1Squared2;
        SecP256R1Point secP256R1Point = new SecP256R1Point(curve, X32, Y32, eCFieldElementArr, this.withCompression);
        return secP256R1Point;
    }

    public ECPoint twicePlus(ECPoint b) {
        if (this == b) {
            return threeTimes();
        }
        if (isInfinity()) {
            return b;
        }
        if (b.isInfinity()) {
            return twice();
        }
        if (this.y.isZero()) {
            return b;
        }
        return twice().add(b);
    }

    public ECPoint threeTimes() {
        if (isInfinity() || this.y.isZero()) {
            return this;
        }
        return twice().add(this);
    }

    public ECPoint negate() {
        if (isInfinity()) {
            return this;
        }
        SecP256R1Point secP256R1Point = new SecP256R1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
        return secP256R1Point;
    }
}