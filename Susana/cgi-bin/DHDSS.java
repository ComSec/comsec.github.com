import java.io.*;
import java.math.BigInteger;
import java.util.Date;


class DHDSS{
        
        //constructor -- called when "new"ed.
	
	public  DHDSS(String[] args) {
		
		// 1024-bit p
		final BigInteger p = new BigInteger("114685974483566629577945645682866797612960470062456909315985356114689596624560155259534568019922582644174012522479850587222012701598381584099048621339427147417888277402197365111117362104221246912753931936128115345533799280211400736494667317497716993108678030197212748797859310760039189193515854868656758162707");
        BigInteger g = new BigInteger("3"); // element in GH(p) with order q
        //BigInteger e = new BigInteger("2"); // primitive element in GH(p) 
      	
      	/*
      	// 256-bit p
		final BigInteger p = new BigInteger("71207881562209882690213888921113757449161374209658144520695854549234021030107");
        BigInteger g = new BigInteger("3"); // element in GH(p) with order q
        BigInteger e = new BigInteger("2"); // primitive element in GH(p) 
	  	*/
      	/*
        final BigInteger p = new BigInteger("23");
  		BigInteger g = new BigInteger("2"); // element in GH(p) with order q
        */
        
        
      	final BigInteger q = p.divide(new BigInteger("2"));
      	
      	int option;
        boolean cont = true;
                
        while (cont) {
        	System.out.println("\nChoose from the followings: ");        
        	System.out.println("1. Compute shared key");        
            System.out.println("2. Sign a hashed value of a message");        
            System.out.println("3. Verify a signature");        
            System.out.println("4. Terminate program");        
            System.out.print("Enter the number of operation you would like to perform: ");
            
            option = Integer.parseInt(ReadInput());
            switch (option) {
            	case 1: DH(p, q, g);break;
            	//case 1: DH(p, q, e);break;
            	case 2: Sign(p, q, g);break;
                case 3: Verify(p, q, g);break; 
                case 4: cont = false;break;
                default: System.out.println("Error");break;
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

	
	private static BigInteger Exponentiation(final BigInteger p, final BigInteger g, final BigInteger K) {
		// this returns g^K mod p
		BigInteger K1 = new BigInteger("0");
		BigInteger num = new BigInteger("0");
		BigInteger result = new BigInteger("1");
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
		//System.out.println(" ");	
			
		/************** Square-and-multiply **************/
		for (int i = 0; i <= r; i++) {
			result = result.multiply(result).mod(p);         
			if (k[i] == 1) result = result.multiply(g).mod(p);
		}
		return result;
	}


	private static void DH(BigInteger p, BigInteger q, BigInteger g) {
      	// 1024-bit p
      	//final BigInteger p = new BigInteger("114685974483566629577945645682866797612960470062456909315985356114689596624560155259534568019922582644174012522479850587222012701598381584099048621339427147417888277402197365111117362104221246912753931936128115345533799280211400736494667317497716993108678030197212748797859310760039189193515854868656758162707");
        //BigInteger g = new BigInteger("2"); // generator
      	boolean cont = true;
      	boolean pri_key = true;
      	Date d1, d2;
        long t1 = 0, t2 = 0, elapsed_time = 0;
      	
      	BigInteger X = null, result;
      	System.out.println("DH Exponentiation in GF(p), where p is " + p);
   		//System.out.println("Primeitive Element, g, is " + g);
   		
      	while (cont){
      		if (pri_key) {
      			//System.out.print("\nEnter your private key x, 0 < x < "+ (p.subtract(new BigInteger("1"))) + ": ");
      			//X = new BigInteger(ReadInput());
      			// Hardcoded for time testing
         		X = new BigInteger("12783595625687579621977255562161969347569439099137834917809831112144561287342004811854044077904037679115154380798468544722795867829496542652238848678382499232452191309724213179050945287241832306768719969765296659373201754985409898316017853288137526101737462066946118685840632612239103255762888336633801531121"); 
      			
      			d1 = new Date();
				t1 = d1.getTime();
      			}	
         	else {
         		//System.out.print("\nEnter the received public key: ");
         		//g = new BigInteger(ReadInput());
         		
         		// Hardcoded for time testing
         		g = new BigInteger("38287117798321484077182028223678091973972920081783219664421034795005018379549551853860462168526246644014658086999719781047693449579239161504864226096433672148488346724614748947654823721019784823129574561947223161666578277307187356521341333530778489857296765535530593514444654651619236288632223127980320760121"); 
         	}
         	
        	result = Exponentiation(p, g, X); // public key
        	
        	if (pri_key) {
        		System.out.println("\nYour public key is: " + result);
        		pri_key = false;
        	}
         	else {
         		System.out.println("\nYour shared secret key is: " + result);
         		d2 = new Date();
                t2 = d2.getTime();
                elapsed_time = t2 - t1;
                System.out.println("Total elapsed time = " + elapsed_time + "ms");
         		cont = false;
         	}
        	//System.out.println("Result is " + result);
        	
    	}
	}



	private static void Sign (BigInteger p, BigInteger q, BigInteger g){
		
		//final BigInteger p = new BigInteger("114685974483566629577945645682866797612960470062456909315985356114689596624560155259534568019922582644174012522479850587222012701598381584099048621339427147417888277402197365111117362104221246912753931936128115345533799280211400736494667317497716993108678030197212748797859310760039189193515854868656758162707");
  		//final BigInteger q = new BigInteger("57342987241783314788972822841433398806480235031228454657992678057344798312280077629767284009961291322087006261239925293611006350799190792049524310669713573708944138701098682555558681052110623456376965968064057672766899640105700368247333658748858496554339015098606374398929655380019594596757927434328379081353");      
        //BigInteger g = new BigInteger("4"); // element in GH(p) with order q
        
        /*
        final BigInteger p = new BigInteger("23");
  		final BigInteger q = new BigInteger("11");      
        BigInteger g = new BigInteger("2"); // element in GH(p) with order q
        */
        /*
        final BigInteger p = new BigInteger("11");
  		final BigInteger q = new BigInteger("5");      
        BigInteger g = new BigInteger("4"); // element in GH(p) with order q
        */
        
        Date d1, d2;
        long t1 = 0, t2 = 0, elapsed_time = 0;
		BigInteger GCD, H, s = null, X = null, K = null, result;
		boolean cont = true;
		
		d1 = new Date();
		t1 = d1.getTime();
		//System.out.print("Select your private key x, 0 < x < " + q + ": ");
		//X = new BigInteger(ReadInput());
		
		// Hardcoded for time testing
		X = new BigInteger("12783595625687579621977255562161969347569439099137834917809831112144561287342004811854044077904037679115154380798468544722795867829496542652238848678382499232452191309724213179050945287241832306768719969765296659373201754985409898316017853288137526101737462066946118685840632612239103255762888336633801531121"); 
		
		result = Exponentiation(p, g, X); // public key
        System.out.println("\nYour public key:" + result);
            
		//System.out.print("Enter a message, m, to be signed: ");
		//H = new BigInteger(ReadInput());
		//System.out.println(" ");
		
		// Hardcoded for time testing
		H = new BigInteger("798974726605473726373578472635123084223089943696114682363114444509035080458875300740877754869002354944697148799904284045174741739343533915764928042398906202028261956857763323690684080452614519173044998110331041210825109686588118644751115830508595381358591379184132417865039538264943953485180521039612595695"); 

				
		while (cont) {
			//System.out.print("Select signing key k, 0 < k < " + q + ": ");
			//K = new BigInteger(ReadInput());
			
			// Hardcoded for time testing
			K = new BigInteger("471270204250005433327620450786220843122134459892041806698344066366194931593862708478983215194395767933875703523139031228212108858493055193271473505329633367146560125299463230217953358663586974476657708882511851829207291372545244566324041009355974966647265254891909479845673413971348105472209757114537821543"); 
			
			result = Exponentiation(p, g, K); // compute r
        	result = result.mod(q);
        	//System.out.println("r = " + result);
			/****** Solve for s ******/
			s = H.subtract(X.multiply(result)).multiply(K.modInverse(q)).mod(q);
			
			//System.out.println("s = " + s );
			if (!s.equals(new BigInteger("0"))) { // no problem
				cont = false;
				System.out.println("The signed message is (" + H + "," + result + "," + s + ")");
				d2 = new Date();
				t2 = d2.getTime();
				//System.out.println("t2 = " + t2);
				elapsed_time += t2 - t1;
                System.out.println("Total elapsed time = " + elapsed_time + "ms");
			}
			else System.out.println("s in signing equation cannot be zero.  Choose another k");
			
		}
	}

	private static void Verify(BigInteger p, BigInteger q, BigInteger g) {
		
		//final BigInteger p = new BigInteger("114685974483566629577945645682866797612960470062456909315985356114689596624560155259534568019922582644174012522479850587222012701598381584099048621339427147417888277402197365111117362104221246912753931936128115345533799280211400736494667317497716993108678030197212748797859310760039189193515854868656758162707");
  		//final BigInteger q = new BigInteger("57342987241783314788972822841433398806480235031228454657992678057344798312280077629767284009961291322087006261239925293611006350799190792049524310669713573708944138701098682555558681052110623456376965968064057672766899640105700368247333658748858496554339015098606374398929655380019594596757927434328379081353");      
        //BigInteger g = new BigInteger("4"); // element in GH(p) with order q        
        /*
        final BigInteger p = new BigInteger("23");
  		final BigInteger q = new BigInteger("11");      
        BigInteger g = new BigInteger("2"); // element in GH(p) with order q
        */
        /*
        final BigInteger p = new BigInteger("11");
  		final BigInteger q = new BigInteger("5");      
        BigInteger g = new BigInteger("4"); // element in GH(p) with order q
        */
        Date d1, d2;
        long t1 = 0, t2 = 0, elapsed_time = 0;
        BigInteger h, r, s, result1,result2;
		//BigInteger u, v, r, t, h, GCD, sx,s_x;
		BigInteger y = new BigInteger("0");
		
		//System.out.println("\nEnter the received public key:");
   		//y = new BigInteger(ReadInput());
		//
		//System.out.println("\nEnter the received message m:");
   		//System.out.print("m = ");
		//h = new BigInteger(ReadInput());
   		//
   		//System.out.println("\nEnter the received digital signature (r, s):");
   		//System.out.print("r = ");
   		//r = new BigInteger(ReadInput());
   		//System.out.print("s = ");
   		//s = new BigInteger(ReadInput());
   		
   		// Hardcoded for time testing
   		y = new BigInteger("71230495082766326375940458974358828143558877738219036753804384650285593443454361832643002489334913169700769361301846356068427188419646265857416900639637775390105083740310121391919958288789838149203563051939222865499164898819201403975697557031562710135321769257761365912232376421923060749450163737121963397166");
   		h = new BigInteger("798974726605473726373578472635123084223089943696114682363114444509035080458875300740877754869002354944697148799904284045174741739343533915764928042398906202028261956857763323690684080452614519173044998110331041210825109686588118644751115830508595381358591379184132417865039538264943953485180521039612595695");
   		r = new BigInteger("38287117798321484077182028223678091973972920081783219664421034795005018379549551853860462168526246644014658086999719781047693449579239161504864226096433672148488346724614748947654823721019784823129574561947223161666578277307187356521341333530778489857296765535530593514444654651619236288632223127980320760121");
   		s = new BigInteger("34133908736438078369792555461344711231292126425080919911184404025567383345879324831652431138964575289061477091466379012046439173224660001730084020087392719349781157840304959294594020032456977788601593548799299463783746351610135497074218877007539402263054928808672803724368133697722620732994952009691998526404");
   		
   		
   		d1 = new Date();
		t1 = d1.getTime();	
		BigInteger w = s.modInverse(q);
		//System.out.println("inverse of " + s + " mod ("+ q + ") is w = " + w);
				
		result1 = Exponentiation(p, g, w.multiply(h).mod(q));
		//System.out.println("g ^ (s^-1 * h(m)) = " + result1);
		
		result2 = Exponentiation(p, y, s.modInverse(q).multiply(new BigInteger("0").subtract(r)).mod(q));
		//System.out.println("y ^ (-r * s^-1) = " + result2);
		result2 = result2.multiply(result1).mod(p).mod(q);
		//System.out.println("final result "+ result2);
		//System.out.println("the entered r is " + r);		   
		if (result2.equals(r)) System.out.println("\nValid signature\n");
		else System.out.println("\nInvalid signature!!\n");
		d2 = new Date();
		t2 = d2.getTime();
		//System.out.println("t2 = " + t2);
		elapsed_time += t2 - t1;
        System.out.println("Total elapsed time = " + elapsed_time + "ms");
	}

	public static void main (String[] args){
		//for (int i = 0; i < args.length; i++)
        //System.out.println(args[i]);
        DHDSS ghObj = new DHDSS(args);
	}

}
