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
my $a = $keys{a_b};
$a =~ s/\,.*$//;

my $b = $keys{a_b};
$b =~ s/^.*\,//;

my $Q;
my $p = $keys{p};

if($keys{p} == "5") {
	$Q = "31";
}
elsif($keys{p} == "7") {
	if (($keys{a_b} == "4,3") || ($keys{a_b} == "3,1") 
	|| ($keys{a_b} == "1,3") || ($keys{a_b} == "3,4")) {
		$Q = "19";
	}
	else {
        	$Q = "57";
	}
}

elsif ($keys{p} == "170") {
	$p ="872506556373043141633422320885297418914216512165511";
        $Q = "1643138524243019099121322635788037230300368850983";
}
else {
	$p = 
"2524100142802065091319986475346620439442782528122381640812816384384364195892628818440024729407595209291";
        $Q = "1647052193950202913767588849369624124585134956111";
}

print "Please wait...";

my $address;
if ($keys{opt} eq "GHDH") {
        $address = "ghdh.cgi";
}
elsif ($keys{opt} eq "Sign") {
        $address = "ghsign.cgi";
}
else {
        $address = "ghver.cgi";

}
$address = $address."?Q=".$Q."&a=".$a."&b=".$b."&p=".$p;
print <<EOL;
 <meta http-equiv="REFRESH" content="2; URL=$address">
EOL

