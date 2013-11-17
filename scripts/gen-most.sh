#! /bin/bash

if [ $# -lt 1 ]
then
    echo "usage: $0 <lib_name>"
    exit 1
fi
LIB_NAME="$1"

source env.sh

# For PROJ_TYPE="clojure-contrib", see below for how the rest of the
# variables are set up.

case ${LIB_NAME} in
algo.generic)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.1.1"
    PROJ_TYPE="clojure-contrib"
    ;;
algo.monads)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.1.4"
    PROJ_TYPE="clojure-contrib"
    ;;
clojure)
    # When I tried this with Clojure 1.5.1 and JDK6, I got this
    # warning message.  This would probably go away if I used JDK7
    # instead.
    # [+] Processing clojure.parallel...
    # Unable to parse /Users/jafinger/clj/thalia/temp-checkouts/clojure/src/clj/clojure/parallel.clj: java.lang.ClassNotFoundException: jsr166y.forkjoin.ParallelArray
    SHORT_NAME=${LIB_NAME}
    VERSION="1.5.1"
    PROJ_TYPE="clojure"
    ;;
core.async)
    # TBD
    ;;
core.cache)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.6.3"
    PROJ_TYPE="clojure-contrib"
    ;;
core.contracts)
    # TBD: lein-clojuredocs 1.0.2 fails on the step "lein clojuredocs"
    # below.  Might be a similar cause to tools.nrepl failure below.
    # Not sure.  Here are 2 lines of output that might be significant:
    #
    # [-] Writing output to core.contracts-0.0.5-SNAPSHOT.json.gz
    # com.fasterxml.jackson.core.JsonGenerationException: Cannot JSON encode object of class: class clojure.lang.Var: #'clojure.core/==
    #
    # It might have something to do with the source file
    # constraints.clj in core.contracts, which has this in the ns
    # declaration near the top, and then later I believe it redefines
    # == in a different way:
    #   (:refer-clojure :exclude [== = not=])
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.0.4 on 2013-11-17
    VERSION="0.0.5"
    PROJ_TYPE="clojure-contrib"
    PROJECT_CLJ_FILE="${LIB_NAME}-${VERSION}-project.clj"
    ;;
core.incubator)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.1.2 on 2013-11-17
    VERSION="0.1.3"
    PROJ_TYPE="clojure-contrib"
    ;;
core.logic)
    # TBD: File src/main/clojure/clojure/core/logic/datomic.clj has
    # two conditional (ns clojure.core.logic.datomic ...)
    # declarations, depending on whether datomic.Datom is a class or
    # not.  This causes tools.namespace to fail to find the ns forms,
    # and thus cadastre and lein-clojuredocs fail to get info about
    # that namespace.
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.8.1 on 2013-11-17
    VERSION="0.8.4"
    PROJ_TYPE="clojure-contrib"
    ;;
core.match)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.2.0-alpha12 on 2013-11-17
    VERSION="0.2.0"
    PROJ_TYPE="clojure-contrib"
    PROJECT_CLJ_FILE="${LIB_NAME}-${VERSION}-project.clj"
    ;;
core.memoize)
    # TBD: lein-clojuredocs 1.0.2 fails on the step "lein clojuredocs"
    # below.  Looks like a different reason than tools.nrepl failure,
    # but not sure.  core.memoize appears to require
    # clojure.core.cache correctly, so I don't understand why there
    # would be an error about clojure.core.cache/through not being
    # defined.
    #
    # Here are 2 lines of output that might be significant:
    #
    # [+] Processing clojure.core.memoize...
    # Unable to parse /Users/jafinger/clj/thalia/temp-checkouts/core.memoize/src/main/clojure/clojure/core/memoize.clj: java.lang.RuntimeException: No such var: clojure.core.cache/through, compiling:(clojure/core/memoize.clj:53:3)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.5.3 on 2013-11-17
    VERSION="0.5.6"
    PROJ_TYPE="clojure-contrib"
    PROJECT_CLJ_FILE="${LIB_NAME}-${VERSION}-project.clj"
    ;;
core.rrb-vector)
    # TBD
    ;;
core.typed)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from master on 2013-11-17
    VERSION="0.2.19"
    PROJ_TYPE="clojure-contrib"
    PROJECT_CLJ_FILE="${LIB_NAME}-${VERSION}-project.clj"
    echo "TBD: Generates a .json.gz file, but with a different version number than what my script expects, so /bin/mv fails."
    exit 1
    ;;
core.unify)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.5.6"
    PROJ_TYPE="clojure-contrib"
    ;;
data.codec)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.1.0"
    PROJ_TYPE="clojure-contrib"
    ;;
data.csv)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.1.2"
    PROJ_TYPE="clojure-contrib"
    ;;
data.finger-tree)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.0.1"
    PROJ_TYPE="clojure-contrib"
    ;;
data.fressian)
    # TBD
    ;;
data.generators)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.1.2"
    PROJ_TYPE="clojure-contrib"
    ;;
data.json)
    # The following comments apply to data.json version 0.2.1, at least:
    # File src/main/clojure/clojure/data/json.clj does (load
    # "json_compat_0_1") near the end, and that file has no 'ns' form,
    # instead beginning with (in-ns 'clojure.data.json), so there is
    # an exception while processing file json_compat_0_1.clj.
    #
    # From examining the output file, it appears that the Vars in file
    # json_compat_0_1.clj are there, although it appears that they are
    # listed as being in file json.clj, not json_compat_0_1.clj.
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.2.1 on 2013-11-17
    VERSION="0.2.3"
    PROJ_TYPE="clojure-contrib"
    ;;
data.priority-map)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.0.2 on 2013-11-17
    VERSION="0.0.4"
    PROJ_TYPE="clojure-contrib"
    ;;
data.xml)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.0.7"
    PROJ_TYPE="clojure-contrib"
    ;;
data.zip)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.1.1"
    PROJ_TYPE="clojure-contrib"
    ;;
java.classpath)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.2.1"
    PROJ_TYPE="clojure-contrib"
    ;;
java.data)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.1.1"
    PROJ_TYPE="clojure-contrib"
    PROJECT_CLJ_FILE="${LIB_NAME}-${VERSION}-project.clj"
    ;;
java.jdbc)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.2.3 on 2013-11-17
    VERSION="0.3.0-beta1"
    PROJ_TYPE="clojure-contrib"
    ;;
java.jmx)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.2.0"
    PROJ_TYPE="clojure-contrib"
    ;;
jvm.tools.analyzer)
    # This comment applies to jvm.tools.analyzer version 0.3.1, at least:
    # TBD: Two source files have (set! *warn-on-reflection* val) forms
    # before their ns forms, which messes up cadastre's ability to
    # extract their namespace name.  I've filed ticket JVMTA-1 to
    # correct this, and will submit a patch to cadastre to make the
    # error message more clear in explaining that this is the issue
    # with the file.
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.3.1 on 2013-11-17
    VERSION="0.5.2"
    PROJ_TYPE="clojure-contrib"
    ;;
math.combinatorics)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.0.3 on 2013-11-17
    VERSION="0.0.7"
    PROJ_TYPE="clojure-contrib"
    ;;
math.numeric-tower)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.0.2"
    PROJ_TYPE="clojure-contrib"
    ;;
test.generative)
    # TBD: Running 'lein clojuredocs' on this one caused Leiningen to
    # get clojure-1.3.0-alpha5 from the Maven repo.  It appears to
    # come through the dependency on tools.namespace 0.1.1.  Updating
    # the dependency to the latest 0.2.2 might be better.  Not urgent,
    # though.

    # TBD: I don't understand the reason for this message in the
    # output:
    # [!] I don't know how to coerce clojure.core$long
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.4.0 on 2013-11-17
    VERSION="0.5.1"
    PROJ_TYPE="clojure-contrib"
    PROJECT_CLJ_FILE="${LIB_NAME}-${VERSION}-project.clj"
    ;;
tools.analyzer)
    # TBD
    ;;
tools.cli)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.2.2 on 2013-11-17
    VERSION="0.2.3"
    PROJ_TYPE="clojure-contrib"
    ;;
tools.logging)
    SHORT_NAME=${LIB_NAME}
    VERSION="0.2.6"
    PROJ_TYPE="clojure-contrib"
    ;;
tools.macro)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.1.2 on 2013-11-17
    VERSION="0.1.5"
    PROJ_TYPE="clojure-contrib"
    ;;
tools.namespace)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.2.2 on 2013-11-17
    VERSION="0.2.4"
    PROJ_TYPE="clojure-contrib"
    ;;
tools.nrepl)
    # This comment applies to tools.nrepl version 0.2.2 at least:
    # TBD: lein-clojuredocs 1.0.2 fails on the step "lein clojuredocs"
    # below.  I've filed an issue on the lein-clojuredocs Github repo
    # on March 22, 2013
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.2.2 on 2013-11-17
    VERSION="0.2.3"
    PROJ_TYPE="clojure-contrib"
    ;;
tools.reader)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.7.3 on 2013-11-17
    VERSION="0.7.10"
    PROJ_TYPE="clojure-contrib"
    ;;
tools.trace)
    SHORT_NAME=${LIB_NAME}
    # TBD: Changed from 0.7.5 on 2013-11-17
    VERSION="0.7.6"
    PROJ_TYPE="clojure-contrib"
    ;;
*)
    echo "Unknown lib name ${LIB_NAME}"
    exit 1
esac

case ${PROJ_TYPE} in
clojure)
    # A few of these need to be different than clojure-contrib
    GIT_URL="git://github.com/clojure/${SHORT_NAME}.git"
    GIT_REPO_ROOT_DIR_NAME="${SHORT_NAME}"
    GIT_TAG_NAME="${SHORT_NAME}-${VERSION}"
    LEIN_PROJ_NAME="clojure.core"
    LEIN_SOURCE_PATH="src/clj"
    LEIN_CLOJUREDOCS_ROOT_FILE_NAME="clojure.core-${VERSION}"
    ;;
clojure-contrib)
    GIT_URL="git://github.com/clojure/${SHORT_NAME}.git"
    GIT_REPO_ROOT_DIR_NAME="${SHORT_NAME}"
    GIT_TAG_NAME="${SHORT_NAME}-${VERSION}"
    LEIN_PROJ_NAME="clojure.${SHORT_NAME}"
    LEIN_SOURCE_PATH="src/main/clojure"
    LEIN_CLOJUREDOCS_ROOT_FILE_NAME="clojure.${SHORT_NAME}-${VERSION}"
    ;;
*)
    echo "Internal script error: unknown PROJ_TYPE ${PROJ_TYPE}"
    exit 1
esac

# After setting the values above, hopefully the rest of this can be common

set -x

cd ${THALIA_TEMP_CHECKOUTS}
git clone ${GIT_URL}
cd ${GIT_REPO_ROOT_DIR_NAME}
if [ x${GIT_TAG_NAME} != x"" ]
then
    git checkout ${GIT_TAG_NAME}
fi
if [ x${PROJECT_CLJ_FILE} != "x" ]
then
    cp ${THALIA_PROJECT_CLJ_DIR}/${PROJECT_CLJ_FILE} project.clj
else
    cat <<EOF > project.clj
(defproject ${LEIN_PROJ_NAME} "${VERSION}"
  :description "${SHORT_NAME} ${VERSION}"
  :eval-in :leiningen
  :source-paths [ "${LEIN_SOURCE_PATH}" ]
  )
EOF
fi
lein clojuredocs
exit_status=$?
if [ ${exit_status} != 0 ]
then
    echo "Exit status ${exit_status} from 'lein clojuredocs.  Aborting."
    exit ${exit_status}
fi
# TBD: Would be good to modify script so that it automatically finds
# the just-created .json.gz file (hopefully the only one that exists
# -- otherwise abort), and use that name here and later.
/bin/mv ${LEIN_CLOJUREDOCS_ROOT_FILE_NAME}.json.gz ${THALIA_CLOJUREDOCS_FILES_DIR}
cd ..
#/bin/rm -fr ${GIT_REPO_ROOT_DIR_NAME}

cd ${THALIA_ROOT}
gzcat ${THALIA_CLOJUREDOCS_FILES_DIR}/${LEIN_CLOJUREDOCS_ROOT_FILE_NAME}.json.gz | lein run json2edn > ${THALIA_CLOJUREDOCS_FILES_DIR}/${LEIN_CLOJUREDOCS_ROOT_FILE_NAME}.clj
