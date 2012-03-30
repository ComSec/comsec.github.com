
my $test = `java GH_cmd 1 5 0 1 31 13 0 2`;
#$test = "(3,4);(3,1);25";
print $test."\n";
$test =~ /(.*)\;(.*)\;(.*)/;
my $public_key = $1;
my $private_key = $2;
my $elapsed_time = $3;
print "$public_key $private_key $elapsed_time";
