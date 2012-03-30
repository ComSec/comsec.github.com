import java.io.*;
import java.math.BigInteger;


class GH2{
        
        //constructor -- called when "new"ed.
	
	public  GH2() {
		
		
		final BigInteger p = new BigInteger("5");
        BigInteger a = new BigInteger("0");
        BigInteger b = new BigInteger("1");
        BigInteger Q = new BigInteger("31");
      	
      	
      	/*
      	final BigInteger p = new BigInteger("7");
        BigInteger a = new BigInteger("5");
        BigInteger b = new BigInteger("1");
        BigInteger Q = new BigInteger("57");
      	*/
      	      	
      	/*
      	// 1024-bit security
      	final BigInteger p = new BigInteger("2524100142802065091319986475346620439442782528122381640812816384384364195892628818440024729407595209291");
        BigInteger a = new BigInteger("1009678462466634534373236165995478977791322864153207149330490776209148279733077179938397109115148708951");
        BigInteger b = new BigInteger("2062160226441847598150245499542278481087087236598545481740882935002939062370689540637392192938836162683");
        BigInteger Q = new BigInteger("1647052193950202913767588849369624124585134956111");
      	*/
      	
      	/*// 512-bit security
      	final BigInteger p = new BigInteger("872506556373043141633422320885297418914216512165511");
        BigInteger a = new BigInteger("547011428656979280882533917968743952565170750950565");
        BigInteger b = new BigInteger("170152303759166890494214567553310676483159608998579");
        BigInteger Q = new BigInteger("1643138524243019099121322635788037230300368850983");
      	*/
      	
      	int option;
        boolean cont = true;
                
        while (cont) {
        	System.out.println("\nChoose from the followings: ");        
        	System.out.println("1. Compute shared key pair");        
            System.out.println("2. Sign a hashed value of a message");        
            System.out.println("3. Verify a signature");        
            System.out.println("4. Terminate program");        
            System.out.print("Enter the number of operation you would like to perform: ");
            
            option = Integer.parseInt(ReadInput());
            switch (option) {
            	case 1: GH_DH(p, a, b, Q);break;
            	case 2: Signature (p, a, b, Q);break;
                case 3: Verify(p, a, b, Q);break; // for testing purpose
                //case 3: System.out.println("Verify signature \n");break;
                case 4: cont = false;break;
			}
		}
	}

	private static String ReadInput(){
		String message = "";
		BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));
		try {
        	message = stdin.readLine();
		}
		catch (Exception ex) {
        	System.out.println ("Exception caught: " + ex.toString());
			System.exit(1);
		}
		return message;
	}

	private static void k_equal_0(final BigInteger p, BigInteger a, BigInteger b, BigInteger[] s){
		//System.out.println("Working with 0");
		BigInteger [] st_prime;
        st_prime = new BigInteger[6] ;
        int run = 0;
        //final BigInteger TWO = new BigInteger("2");
        
		while (run < 2) {
			st_prime[0+run*3] = ((s[1+run*3].multiply(s[0+run*3]).subtract(s[(4+run*3)%6].multiply(b))).add(s[(5+run*3)%6])).mod(p);
			st_prime[1+run*3] = ((s[1+run*3].multiply(s[1+run*3])).subtract((new BigInteger("2")).multiply(s[(4+run*3)%6]))).mod(p);
			st_prime[2+run*3] = ((s[1+run*3].multiply(s[2+run*3])).subtract(s[(4+run*3)%6].multiply(a)).add(s[(3+run*3)%6])).mod(p);
			System.out.println(st_prime[0+run*3] + "," + st_prime[1+run*3] + "," + st_prime[2+run*3]); 
			run++;
			BigInteger temp = a;
			a = b;
			b = temp;
		}
   		for (int z = 0; z < 6; z++) s[z] = st_prime[z];
	}
	
	private static void k_equal_1(final BigInteger p, BigInteger a, BigInteger b, BigInteger[] s){
		//System.out.println("Working with 1");
		BigInteger [] st_prime;
        st_prime = new BigInteger[6] ;
        int run = 0;
        //final BigInteger TWO = new BigInteger("2");

		while (run < 2) {
			st_prime[0+run*3] = ((s[1+run*3].multiply(s[1+run*3])).subtract((new BigInteger("2")).multiply(s[(4+run*3)%6]))).mod(p);
			st_prime[1+run*3] = ((s[1+run*3].multiply(s[2+run*3])).subtract(s[(4+run*3)%6].multiply(a)).add(s[(3+run*3)%6])).mod(p);
			st_prime[2+run*3] = ((s[2+run*3].multiply(s[2+run*3])).subtract((new BigInteger("2")).multiply(s[(5+run*3)%6]))).mod(p);
			System.out.println(st_prime[0+run*3] + "," + st_prime[1+run*3] + "," + st_prime[2+run*3]); 
			run++;
			BigInteger temp = a;
			a = b;
			b = temp;
   		}
   		for (int z = 0; z < 6; z++) s[z] = st_prime[z];
	}
	
	private static void Seq_term(final BigInteger p, final BigInteger a, final BigInteger b, BigInteger[] s, BigInteger K) {
		BigInteger K1 = new BigInteger("0");
		BigInteger num = new BigInteger("0");
		//final BigInteger TWO = new BigInteger("2");
		int r = 0;
		
		/**** Convert K to binary rep ****/
		K1 = K;
		while (K.compareTo((new BigInteger("2")).pow(r)) == 1) r++; 
		//System.out.println("r = "+ r);
		if (K.compareTo((new BigInteger("2")).pow(r)) != 0) r--;
		int k[] = new int[r+1];		
		k[0] = 1; // MSB
		//System.out.print(k[0]);
		K1 = K1.subtract((new BigInteger("2")).pow(r));
		for (int i = 1; i <= r; i++) {
			if (K1.compareTo((new BigInteger("2")).pow(r-i)) != -1) {
				k[i] = 1;
				K1 = K1.subtract((new BigInteger("2")).pow(r-i));
			}
			else k[i] = 0;
		//	System.out.print(k[i]);			
		}
		System.out.println(" ");	
			
		/********* Initial State *********/
		s[0] = new BigInteger("3");
   		s[1] = a;
   		s[2] = ((a.multiply(a)).subtract((new BigInteger("2")).multiply(b))).mod(p);
   		s[3] = new BigInteger("3");
   		s[4] = b;
   		s[5] = ((b.multiply(b)).subtract((new BigInteger("2")).multiply(a))).mod(p);
		
		//for (int i = 0; i<=5; i++) System.out.print(s[i] +" ");
		//System.out.println(" ");
		
		/************** DSEA **************/
		for (int i = 1; i <= r; i++) {
			if (k[i] == 0) k_equal_0(p, a, b, s);
			else k_equal_1(p, a, b, s);
		}
	}

	private static void GH_DH(BigInteger p, BigInteger a, BigInteger b, BigInteger Q) {
		BigInteger X = new BigInteger("0");
        BigInteger [] s;
        s = new BigInteger[6] ;
        boolean pri_key = true;
        int cont = 1;
        BigInteger GCD = new BigInteger("0");
        //final BigInteger ONE = new BigInteger("1");
        
        while (cont == 1) {
        	if (pri_key){ // Select Private Key
        		System.out.print("Select your private key x, 0 < x < " + Q + ": "); 
            	X = new BigInteger(ReadInput());
            	GCD = X.gcd (Q);
            	//System.out.println("gcd = " + GCD);
            	while (!GCD.equals((new BigInteger("1")))) {
            		System.out.print("X is not co-prime with Q.  Select another x, 0 < x < " + Q + ": ");
            		X = new BigInteger(ReadInput());
            		GCD = X.gcd (Q);
            		//System.out.println("X = " + X);
            		//System.out.println("Q = " + Q);
            		//System.out.println("gcd = " + GCD);
            	}
			}
			else { // Shared Key Computation
				System.out.println("Enter the received key pair:");
				System.out.print("sx = ");
				a = new BigInteger(ReadInput());
            	System.out.print("s-x = ");
				b = new BigInteger(ReadInput());
			}
			Seq_term(p, a, b, s, X);
			if (pri_key) { // Output Public Key Pair
				System.out.println("Your public key pair is (" + s[1] + "," + s[4] + ")");
				pri_key = false;
			}
			else { // Output Shared Key Pair
				System.out.println("Your shared key pair is (" + s[1] + "," + s[4] + ")");
				System.out.print("Any more computation? (Input 1 = Yes, 0 = No) ");
				cont = Integer.parseInt(ReadInput());
				
			}

		}		
    }

	private static void Signature (final BigInteger p, final BigInteger a, final BigInteger b, final BigInteger Q){
		BigInteger GCD, H, t, X, K;
		X = new BigInteger("0");
		K = new BigInteger("0");
		BigInteger[] s;
		s = new BigInteger[6];
		boolean delta_r_flag = true;
		boolean rt_flag = true;
		//BigInteger ZERO = new BigInteger("0");
		//BigInteger ONE = new BigInteger("1");
		
		/*
		//Test Inv:
		BigInteger test_inv = new BigInteger("2");
        System.out.println(test_inv + "^-1 mod Q = " + test_inv.modInverse(Q));
        test_inv = new BigInteger("4");
        System.out.println(test_inv + "^-1 mod Q = " + test_inv.modInverse(Q));
        test_inv = new BigInteger("5");
        System.out.println(test_inv + "^-1 mod Q = " + test_inv.modInverse(Q));
        test_inv = new BigInteger("7");
        System.out.println(test_inv + "^-1 mod Q = " + test_inv.modInverse(Q));
        test_inv = new BigInteger("10");
        System.out.println(test_inv + "^-1 mod Q = " + test_inv.modInverse(Q));
        test_inv = new BigInteger("11");
        System.out.println(test_inv + "^-1 mod Q = " + test_inv.modInverse(Q));
		*/
		
		
		while (delta_r_flag) { // Choose x such that delta != 0
			System.out.print("Select your private key x, 0 < x < " + Q + ": ");
			X = new BigInteger(ReadInput());
			GCD = X.gcd(Q);
			while (!GCD.equals((new BigInteger("1")))) {
            	System.out.print("X is not co-prime with Q.  Select another x, 0 < x < " + Q + ": ");
            	X = new BigInteger(ReadInput());
            	GCD = X.gcd (Q);
            	//System.out.println("X = " + X);
            	//System.out.println("Q = " + Q);
            	//System.out.println("gcd = " + GCD);
            }
            Seq_term(p, a, b, s, X);
            
            System.out.println(X);
            System.out.println(s[0] + "," + s[1] + "," + s[2]);
            System.out.println(s[3] + "," + s[4] + "," + s[5]);
            
            if (!((s[2].multiply(s[5])).subtract(a.multiply(b))).mod(p).equals((new BigInteger("0")))) {
            	delta_r_flag = false;
            	System.out.println("Your public key pair is:");
            	System.out.println("(sx, sx+1) = {" + s[1] + "," + s[2] + ")");
            	System.out.println("(s-x, s-(x+1)) = {" + s[4] + "," + s[5] + ")\n");
            }
            else System.out.println("Delta is Zero for the chosen x.  Choose another x.");
		}
		
		System.out.print("Enter a message, m, to be signed: ");
		H = new BigInteger(ReadInput());
		System.out.println(" ");
		
		while (rt_flag) { // Choose k such that both r and t != 0 and delta != 0
			delta_r_flag = true;
			while (delta_r_flag){ // Choose k such that delta != 0 and gcd(r, Q) = 1
				System.out.print("Select signing key k, 0 < k < " + Q + ": ");
				K = new BigInteger(ReadInput());
				GCD = K.gcd (Q);
				while (!GCD.equals((new BigInteger("1")))) {
            		System.out.print("k is not co-prime with Q.  Select another k, 0 < k < " + Q + ": ");
            		K = new BigInteger(ReadInput());
            		GCD = K.gcd (Q);
            		//System.out.println("X = " + X);
            		//System.out.println("Q = " + Q);
            		//System.out.println("gcd = " + GCD);
            	}
            	Seq_term(p, a, b, s, K);
            	GCD = s[1].gcd(Q);
            	
            	if (!GCD.equals((new BigInteger("1")))) System.out.println("sk is not co-prime with Q.  Choose another k.");
            	else {
            		//System.out.println(((s[2].multiply(s[5])).subtract(a.multiply(b))).mod(p));
            		if (!((s[2].multiply(s[5])).subtract(a.multiply(b))).mod(p).equals((new BigInteger("0")))) delta_r_flag = false;
            		else System.out.println("Delta is Zero for the chosen k.  Choose another k.");
            	}
            	
			}
			/****** Compute Signature ******/
			t = (H.subtract(X.multiply(s[1])).multiply(K.modInverse(Q))).mod(Q);
			//System.out.println("t = " + t );
			if (!s[1].equals((new BigInteger("0"))) && !t.equals((new BigInteger("0")))) {
				rt_flag = false; 
         		System.out.println("The signed message is (" + H + "," + s[1] + "," + t + ")");
         		System.out.print("(sk, sk+1) is its dual are (" + s[1] + "," + s[2] + ")");
         		System.out.println(" and (" + s[4] + "," + s[5] + ") respectively\n");
			}
			else System.out.println("Either one of the resultant signature parameter is Zero.  Pick another k");			
		}

		
	}

	private static void InvM0(final BigInteger p, final BigInteger a, final BigInteger b, BigInteger[] M0) {
	//private static void InvM0(final BigInteger p, final BigInteger a, final BigInteger b) { // for testing purpose
		BigInteger det = new BigInteger("0"); 
		//BigInteger[] M0 = new BigInteger[9]; // FOR TESTING PURPOSE
		
	
		det = (b.multiply(b).subtract(new BigInteger("2").multiply(a)))
		.multiply( new BigInteger("3").multiply(a.multiply(a).subtract(new BigInteger("2").multiply(b))).subtract(a.multiply(a)));
		det = det.subtract(b.multiply(b.multiply(a.multiply(a).subtract(new BigInteger("2").multiply(b))).subtract(new BigInteger("3").multiply(a))));	
		det = det.add(new BigInteger("3").multiply(a.multiply(b).subtract(new BigInteger("3").multiply(new BigInteger("3")))));
		det = det.mod(p);
		//System.out.println("det = " + det);
		det = det.modInverse(p);
		//System.out.println("det^-1 mod p = " + det); 
		
		M0[0] = (new BigInteger("3").multiply(a.multiply(a).subtract(new BigInteger("2").multiply(b))).subtract(a.multiply(a))).multiply(det).mod(p);
		M0[1] = (new BigInteger("0").subtract(b.multiply(a.multiply(a).subtract(new BigInteger("2").multiply(b))).subtract(new BigInteger("3").multiply(a)))).multiply(det).mod(p);
		M0[2] = (a.multiply(b).subtract(new BigInteger("3").multiply(new BigInteger("3")))).multiply(det).mod(p);
		M0[3] = (new BigInteger("0").subtract(b.multiply((a.multiply(a).subtract(new BigInteger("2").multiply(b)))).subtract(new BigInteger("3").multiply(a)))).multiply(det).mod(p);
		M0[4] = ((b.multiply(b).subtract(new BigInteger("2").multiply(a))).multiply(a.multiply(a).subtract(new BigInteger("2").multiply(b)))
		 		.subtract(new BigInteger("3").multiply(new BigInteger("3")))).multiply(det).mod(p);
		M0[5] = (new BigInteger("0").subtract(a.multiply(b.multiply(b).subtract(new BigInteger("2").multiply(a))).subtract(new BigInteger("3").multiply(b)))).multiply(det).mod(p);
		M0[6] = (a.multiply(b).subtract(new BigInteger("3").multiply(new BigInteger("3")))).multiply(det).mod(p);
		M0[7] = (new BigInteger("0").subtract(a.multiply(b.multiply(b).subtract(new BigInteger("2").multiply(a))).subtract(new BigInteger("3").multiply(b)))).multiply(det).mod(p);   
		M0[8] = (new BigInteger("3").multiply((b.multiply(b).subtract(new BigInteger("2").multiply(a)))).subtract(b.multiply(b))).multiply(det).mod(p); 
		
		//for (int i = 0; i <= 8; i++) System.out.print(M0[i] + " ");
		//System.out.println(" ");
		
	}	

	private static void sk_plus_v(final BigInteger p, BigInteger a, BigInteger b, final BigInteger v, BigInteger[] s){
		BigInteger[] M0,M0Mv,sv;
		sv = new BigInteger[6];
		M0 = new BigInteger[9];
		M0Mv = new BigInteger[6];
		BigInteger temp;
		int run = 0;
		
		Seq_term(p, a, b, sv, v);
		
		while (run < 2) {
			InvM0(p, a, b, M0);	// For testing purpose
			for (int i = 0; i<=2; i++) {// Center column of M0^-1 * Mv
				M0Mv[i + run * 3] = ((M0[i * 3].multiply(sv[0 + run * 3]))
				.add(M0[i * 3 + 1].multiply(sv[1 + run * 3]))
				.add(M0[i * 3 + 2].multiply(sv[2 + run * 3]))).mod(p);
			}
			
			sv[1 + run * 3] = new BigInteger("0");
			for (int i = 0 + run * 3; i <= 2 + run * 3; i++) 
         		sv[1 + run * 3] = (sv[1 + run * 3].add(s[i].multiply(M0Mv[i]))).mod(p);
      		temp = a;
      		a = b;
      		b = temp; 
      		run ++;  
		}
		s[1] = sv[1];
		s[4] = sv[4];
	   	//System.out.println("sk+v = " + sv[1]); 
   		//System.out.println("s-(k+v) = " + sv[4]); 
	}
	
	
	private static void Mixed_term(final BigInteger p, BigInteger a, BigInteger b, final BigInteger u, final BigInteger v, BigInteger[] s){
		if (!v.equals(new BigInteger("0"))) sk_plus_v(p, a, b, v, s); // to compute sk+v and its dual
		Seq_term(p, s[1], s[4], s, u);
	}
	
	
	private static void Previous_term(final BigInteger p, BigInteger a, BigInteger b, BigInteger[] s){
	//private static void Previous_term(final BigInteger p, BigInteger a, BigInteger b){ // For TESTING PURPOSE
		BigInteger delta, c1, c2, e, Dc1, Dc2, De;
		
		/*
		BigInteger[] s = new BigInteger[6]; // FOR TESTING PURPOSE
		s[0] = new BigInteger("0"); // FOR TESTING PURPOSE
		s[1] = new BigInteger("2"); // FOR TESTING PURPOSE
		s[2] = new BigInteger("4"); // FOR TESTING PURPOSE
		s[3] = new BigInteger("0"); // FOR TESTING PURPOSE
		s[4] = new BigInteger("0"); // FOR TESTING PURPOSE
		s[5] = new BigInteger("1"); // FOR TESTING PURPOSE
		*/
		
   		/** Agorithm 2: Compute sk-1 and its dual using (sk, sk+1) and its dual **/
   		delta = (s[2].multiply(s[5]).subtract(a.multiply(b))).mod(p);
   		//System.out.println("Delta is " + delta);
   		
   		delta = delta.modInverse(p); // delta^-1
   		//System.out.println("Delta^-1 mod p is " + delta);
   		
   		c1 = (a.multiply(s[2]).subtract(b.multiply(s[1]))).mod(p);
   		Dc1 = (b.multiply(s[5]).subtract(a.multiply(s[4]))).mod(p);
   		c2 = (s[1].multiply(s[1]).subtract(new BigInteger("3").multiply(s[4])).add((b.multiply(b).subtract(a).multiply(s[5])))).mod(p);
   		Dc2 = (s[4].multiply(s[4]).subtract(new BigInteger("3").multiply(s[1])).add((a.multiply(a).subtract(b).multiply(s[2])))).mod(p);
   		e = (new BigInteger("0").subtract(b).multiply(Dc1).add(c2)).mod(p);
   		De = (new BigInteger("0").subtract(a).multiply(c1).add(Dc2)).mod(p);
   		s[0] = (e.multiply(s[5]).subtract(b.multiply(De))).multiply(delta).mod(p);
   		s[3] = (De.multiply(s[2]).subtract(a.multiply(e))).multiply(delta).mod(p);

   		/*
   		System.out.println("\nc1 = " + c1);
   		System.out.println("Dc1 = " + Dc1);
   		System.out.println("c2 = " + c2);
   		System.out.println("Dc2 = " + Dc2);                              
   		System.out.println("e = " + e);
   		System.out.println("De = " + De);                              
   		//System.out.println(c1 +" "+ Dc1 + " " + c2 + " " + Dc2 + " " + e + " " + De);
   		
   		System.out.println("\nsk-1 = " + s[0] );
   		System.out.println("s-(k-1) = " + s[3] );
		*/
	}
	

	private static void Verify(final BigInteger p, BigInteger a, BigInteger b, final BigInteger Q) {
		BigInteger u, v, r, t, h, GCD, sx,s_x;
		sx= new BigInteger("0");
		s_x= new BigInteger("0");
		BigInteger[] s = new BigInteger[6];
		
		System.out.println("\nEnter the received message m:");
   		System.out.print("m = ");
		h = new BigInteger(ReadInput());
   		

   		System.out.println("\nEnter the received digital signature (r, t):");
   		System.out.print("r = ");
   		r = new BigInteger(ReadInput());
   		System.out.print("t = ");
   		t = new BigInteger(ReadInput());
	
		GCD = t.gcd (Q);
		
		if (GCD.equals(new BigInteger("1"))) { // Case 1
			System.out.println("Case 1");	
			System.out.println("\nEnter the received (sk, sk+1):");
      		System.out.print("sk = ");
      		s[1] = new BigInteger(ReadInput());
      		System.out.print("sk+1 = ");
      		s[2] = new BigInteger(ReadInput());

			System.out.println("\nEnter the received (s-k, s-(k+1)):");
      		System.out.print("s-k = ");
      		s[4] = new BigInteger(ReadInput());
      		System.out.print("s-(k+1) = ");
      		s[5] = new BigInteger(ReadInput());
      		
      		Previous_term(p, a, b, s);
			
			v = t.modInverse(Q); // t^-1 mod Q
			//System.out.println("\nInverse of t is " + v);			
			v = new BigInteger("0").subtract(h).multiply(v).mod(Q);
			//System.out.println("\nv = " + v);			
			u = r.modInverse(Q); // r^-1 mod Q
			//System.out.println("\nInverse of r is " + u);
			u = new BigInteger("0").subtract(t).multiply(u).mod(Q);
			//System.out.println("u = " + u);	
			Mixed_term(p, a, b, u, v, s);		
			//System.out.println("The received public key pair (sx, s-x) should be (" + 
      		//s[1] + ", " + s[4] + ")");
			System.out.println("\nEnter the received (sx, s-x):");
      		System.out.print("sx = ");
      		sx = new BigInteger(ReadInput());
      		System.out.print("s-x = ");
      		s_x = new BigInteger(ReadInput());
			
		}
		
		else { // Case 2
			System.out.println("Case 2");
			System.out.println("\nEnter the received (sx, sx+1):");
      		System.out.print("sx = ");
      		s[1] = new BigInteger(ReadInput());
      		System.out.print("sx+1 = ");
      		s[2] = new BigInteger(ReadInput());
      		System.out.println("\nEnter the received (s-x, s-(x+1)):");
      		System.out.print("s-x = ");
      		s[4] = new BigInteger(ReadInput());
      		System.out.print("s-(x+1) = ");
      		s[5] = new BigInteger(ReadInput());
      		
      		Previous_term(p, a, b, s);
			
			v = r.modInverse(Q); // r^-1 mod Q
			v = new BigInteger("0").subtract(h).multiply(v).mod(Q);
			//System.out.println("\nv = " + v);			
			u = new BigInteger("0").subtract(r).mod(Q);
			//System.out.println("u = " + u);	
			Mixed_term(p, a, b, u, v, s);
			
			sx = s[1]; // This is actually skt
      		s_x = s[4];
			
			System.out.println("\nEnter the received (sk and s-k):");
      		System.out.print("sk = ");
      		s[1] = new BigInteger(ReadInput());
      		System.out.print("s-k = ");
      		s[4] = new BigInteger(ReadInput());
      		Mixed_term(p, a, b, t, new BigInteger("0"), s);
		
		}
		   
		if ((s[1].equals(sx)) && (s[4].equals(s_x))) System.out.println("\nValid signature\n");
		else System.out.println("\nInvalid signature!!\n");
	}
	
	   
	public static void main (String[] args) throws IOException {
		GH2 ghObj = new GH2();
	}

}
