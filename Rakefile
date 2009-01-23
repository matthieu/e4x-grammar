gem "buildr", "~>1.3"
require "buildr"
require "buildr/antlr"

VERSION_NUMBER = "0.2"
ANTLR = "org.antlr:antlr:jar:3.0.1"
repositories.remote << "http://repo1.maven.org/maven2"

desc "E4X Island Grammar"
define "e4x-grammar" do
  # Project settings
  project.version = VERSION_NUMBER
  project.group = "uk.co.badgersinfoil.e4x"

  compile.options.source = "1.5"
  compile.options.target = "1.5"

  # Building
  pkg_name = "uk.co.badgersinfoil.e4x.antlr"
  compile.from antlr(_("src/main/antlr"), :in_package=>pkg_name)
  compile.with ANTLR
  package(:jar)
end
