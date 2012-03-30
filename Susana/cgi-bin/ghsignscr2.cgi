#!/.software/local/.admin/bins/bin/perl
use strict;
use Env;
use CGI;

print "Content-type:text/html\n\n";
my %keys;
my $cgi = new CGI;

foreach my $key ( $cgi->param() ) {
	$keys{$key} = $cgi->param($key);
}
my $output=`java GH_cmd 2 $keys{p} $keys{a} $keys{b} $keys{Q} $keys{X} $keys{h} $keys{K}`; 

$output =~ /(.*)\;(.*)\;(.*)\;(.*)\;(.*)/;
my $public_key1 = $1;
my $public_key2 = $2;
my $signed_mess = $3;
my $sk1 = $4;
my $sk2 = $5;

$public_key1 =~ /\(([0-9]*)\,([0-9]*)\)/;
my $sx = $1;
my $sxp1 = $2;

$public_key2 =~ /\(([0-9]*)\,([0-9]*)\)/;
my $s_x = $1;
my $s_xp1 = $2;

$signed_mess =~ /\(([0-9]*)\,([0-9]*)\,([0-9]*)\)/;
my $h = $1;
my $r = $2;
my $t = $3;


$sk1 =~ /\(([0-9]*)\,([0-9]*)\)/;
my $sk = $1;
my $skp1 = $2;

$sk2 =~ /\(([0-9]*)\,([0-9]*)\)/;
my $s_k = $1;
my $s_kp1 = $2;

print <<EOL;
<h1> GH Signature Result</h1>
<br>
Your public key:<br>
(sx, sx+1) is $public_key1 <br>
(s-x, s-(x+1)) is $public_key2 <p>
The signed message is $signed_mess <p>
(sk, sk+1) is $sk1 <br>
(s-k, s-(k+1)) is $sk2 <p>

<form method="POST" name="verify" action = "ghverscr2.cgi">
<input type="hidden" name="p" value=$keys{p}>
<input type="hidden" name="a" value=$keys{a}>
<input type="hidden" name="b" value=$keys{b}>
<input type="hidden" name="Q" value=$keys{Q}>

<input type="hidden" name="h" value=$h>
<input type="hidden" name="r" value=$r>
<input type="hidden" name="t" value=$t>

<input type="hidden" name="sx" value=$sx>
<input type="hidden" name="sxp1" value=$sxp1>
<input type="hidden" name="s_x" value=$s_x>
<input type="hidden" name="s_xp1" value=$s_xp1>

<input type="hidden" name="sk" value=$sk>
<input type="hidden" name="skp1" value=$skp1>
<input type="hidden" name="s_k" value=$s_k>
<input type="hidden" name="s_kp1" value=$s_kp1>

<input type="submit" value="Verify Signature">

</form>




EOL



