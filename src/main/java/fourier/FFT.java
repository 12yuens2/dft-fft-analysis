package fourier;
/*************************************************************************
 *  Compilation:  javac FFT.java
 *  Dependencies: Complex.java
 *
 *************************************************************************/


public class FFT {

    // compute the FFT of x[], assuming its length is a power of 2
    public static Complex[] fft(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1) {
        	return new Complex[] { x[0] };
        }

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) { throw new IllegalArgumentException("N is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }
    
    /**
     * Small optimisation of FFT.fft()
     */
    public static Complex[] fftSimpleOptimisation(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1) {
        	return new Complex[] { x[0] };
        }

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) { throw new IllegalArgumentException("N is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            Complex t = wk.times(r[k]);
            y[k]       = q[k].plus(t);
            y[k + N/2] = q[k].minus(t);
        }
        return y;
    }
    
    /**
     * Function taken from http://introcs.cs.princeton.edu/java/97data/InplaceFFT.java.html
	 * Reference:  Algorithm 1.6.1 in Computational Frameworks for the Fast Fourier Transform by Charles Van Loan.
     */
    public static void fftInplace(Complex[] x) {

        // check that length is a power of 2
        int N = x.length;
        if (Integer.highestOneBit(N) != N) {
            throw new RuntimeException("N is not a power of 2");
        }

        // bit reversal permutation
        int shift = 1 + Integer.numberOfLeadingZeros(N);
        for (int k = 0; k < N; k++) {
            int j = Integer.reverse(k) >>> shift;
            if (j > k) {
                Complex temp = x[j];
                x[j] = x[k];
                x[k] = temp;
            }
        }

        // butterfly updates
        for (int L = 2; L <= N; L = L+L) {
            for (int k = 0; k < L/2; k++) {
                double kth = -2 * k * Math.PI / L;
                Complex w = new Complex(Math.cos(kth), Math.sin(kth));
                for (int j = 0; j < N/L; j++) {
                    Complex tao = w.times(x[j*L + k + L/2]);
                    x[j*L + k + L/2] = x[j*L + k].minus(tao); 
                    x[j*L + k]       = x[j*L + k].plus(tao); 
                }
            }
        }
    }
      
}
