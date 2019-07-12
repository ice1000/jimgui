#!/usr/bin/env perl

use warnings FATAL => 'all';
use strict;
use v5.15;

foreach my $arch (qw(X64 X86)) {
    my $dir = "Debug$arch";
    `rm -rf $dir`;
    say `mkdir -p $dir`;
    chdir $dir;
    say `cmake .. -D$arch=true -G "Unix Makefiles"`;
    say `make`;
    chdir '..';
}
