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

print <<EOL;

<form name="ghverinput" action="ghverscr2.cgi" method="GET">
<input type="hidden" name="p" value=$keys{p}>
<input type="hidden" name="a" value=$keys{a}>
<input type="hidden" name="b" value=$keys{b}>
<input type="hidden" name="Q" value=$keys{Q}>

Enter received data: <br>
m = <input type=text name="h"><br>
r = <input type=text name="r"><br>
t = <input type=text name="t"><p>

sx = <input type=text name="sx"><br>
sx+1 = <input type=text name="sxp1"><br>
s-x = <input type=text name="s_x"><br>
s-(x+1) = <input type=text name="s_xp1"><p>

sk = <input type=text name="sk"><br>
sk+1 = <input type=text name="skp1"><br>
s-k = <input type=text name="s_k"><br>
s-(k+1) = <input type=text name="s_kp1"><p>

<input type = "submit" value="Send"> 
<input type = "reset" value="Clear Form">

</form>
EOL

