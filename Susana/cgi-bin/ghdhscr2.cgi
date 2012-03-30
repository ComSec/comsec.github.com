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
my $output=`java GH_cmd 1 $keys{p} $keys{a} $keys{b} $keys{Q} $keys{X} $keys{sy} $keys{s_y}`; 

$output =~ /(.*)\;(.*)\;(.*)/;
my $public_key1 = $1;
my $public_key2 = $2;
my $elapsed_time = $3;

print <<EOL;
<h1> GH-DH Key Agreement Result</h1>
<br>
Your public key pair is $public_key1 <br>
Your shared key pair is $public_key2 <p>
Elapsed time: $elapsed_time ms<p>
EOL



