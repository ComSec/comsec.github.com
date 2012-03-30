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
my $output=`java GH_cmd 3 $keys{p} $keys{a} $keys{b} $keys{Q} $keys{h} $keys{r} $keys{t} $keys{sx} $keys{sxp1} $keys{s_x} $keys{s_xp1} $keys{sk} $keys{skp1} $keys{s_k} $keys{s_kp1}`; 

$output =~ /(.*)\;(.*)/;
my $result = $1;
my $elapsed_time = $2;


 print <<EOL;
<h1> GH Signature Verification Result</h1>
<br>
The signature is $result <p>
Elapsed time is $elapsed_time ms
EOL



