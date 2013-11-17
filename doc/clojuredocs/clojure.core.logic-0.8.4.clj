{"namespaces"
 {"clojure.core.logic.protocols"
  [{"ns" "clojure.core.logic.protocols",
    "name" "IWithPrefix",
    "line" 215,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IWithPrefix\n  (-with-prefix [c p]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IUnifyWithPMap",
    "line" 221,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IUnifyWithPMap\n  (unify-with-pmap [pmap u s]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["t" "fc" "s"]],
    "name" "-constrain-tree"}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.protocols",
    "name" "non-storable?",
    "column" 1,
    "line" 29,
    "source" "(defn non-storable? [x]\n  (instance? INonStorable x))",
    "file" "clojure/core/logic/protocols.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "ISubstitutions",
    "line" 45,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol ISubstitutions\n  (ext-no-check [this x v])\n  (walk [this x]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["v" "x"]],
    "name" "-force-ans"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x" "v"]],
    "name" "update-var"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IDisunifyTerms",
    "line" 204,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IDisunifyTerms\n  (-disunify-terms [u v s cs]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x"]],
    "name" "walk"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "argv" "cache" "start" "end"]],
    "name" "reuse"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["dom"]],
    "name" "-member-count"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IIfU",
    "line" 82,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IIfU\n  (ifu [b gs c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IEntailedVar",
    "line" 146,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IEntailedVar\n  (-entailed-var? [c x]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "arity"]],
    "name" "indexes-for"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this"]],
    "name" "ready?"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IBind",
    "line" 67,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IBind\n  (bind [this g]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic.protocols",
    "name" "id",
    "column" 1,
    "line" 157,
    "source"
    "(defn id [c]\n  (if (instance? clojure.core.logic.protocols.IConstraintId c)\n    (-id c)\n    (-> c meta ::id)))",
    "file" "clojure/core/logic/protocols.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IWithConstraintId",
    "line" 151,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IWithConstraintId\n  (-with-id [c id]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "ISubstitutionsCLP",
    "line" 112,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol ISubstitutionsCLP\n  (root-val [this x])\n  (root-var [this x])\n  (ext-run-cs [this x v])\n  (queue [this c])\n  (update-var [this x v]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "ITake",
    "line" 73,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol ITake\n  (take* [a]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["v" "f"]],
    "name" "walk-term"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IConstraintWatchedStores",
    "line" 167,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IConstraintWatchedStores\n  (-watched-stores [c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["u" "v" "s"]],
    "name" "unify-terms"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IConstraintStep",
    "line" 135,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IConstraintStep\n  (-step [c s]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c"]],
    "name" "-prefix"}
   {"ns" "clojure.core.logic.protocols",
    "name" "ITabled",
    "line" 96,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol ITabled\n  (-reify-tabled [this v])\n  (reify-tabled [this v])\n  (reuse [this argv cache start end])\n  (subunify [this arg ans]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x" "root"]],
    "name" "migrate"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IEntailed",
    "line" 143,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IEntailed\n  (-entailed? [c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IConstraintId",
    "line" 154,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IConstraintId\n  (-id [c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c" "p"]],
    "name" "-with-prefix"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IReifyTerm",
    "line" 52,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IReifyTerm\n  (reify-term [v s]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IMergeDomains",
    "line" 192,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IMergeDomains\n  (-merge-doms [a b]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IUnifyTerms",
    "line" 21,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IUnifyTerms\n  (unify-terms [u v s]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c" "x"]],
    "name" "-entailed-var?"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "arity" "f"]],
    "name" "setfn"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["v" "x" "s"]],
    "name" "occurs-check-term"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["u" "v" "s"]],
    "name" "unify-with-record"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["x"]],
    "name" "-feature"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x" "v"]],
    "name" "ext-no-check"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "c"]],
    "name" "queue"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IConstraintStore",
    "line" 122,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IConstraintStore\n  (addc [this a c])\n  (updatec [this a c])\n  (remc [this a c])\n  (runc [this c state])\n  (constraints-for [this a x ws])\n  (migrate [this x root]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x"]],
    "name" "-cached?"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IRel",
    "line" 88,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IRel\n  (setfn [this arity f])\n  (indexes-for [this arity])\n  (add-indexes [this arity index]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "LConsPrint",
    "line" 39,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol LConsPrint\n  (toShortString [this]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IOccursCheckTerm",
    "line" 58,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IOccursCheckTerm\n  (occurs-check-term [v x s]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this"]],
    "name" "lfirst"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c" "s"]],
    "name" "-step"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IMPlus",
    "line" 70,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IMPlus\n  (mplus [a f]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["b" "gs" "c"]],
    "name" "ifu"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this"]],
    "name" "lnext"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IMemberCount",
    "line" 195,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IMemberCount\n  (-member-count [dom]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x"]],
    "name" "root-val"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c"]],
    "name" "-rands"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IForceAnswerTerm",
    "line" 198,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IForceAnswerTerm\n  (-force-ans [v x]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IUnwrapConstraint",
    "line" 187,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IUnwrapConstraint\n  (-unwrap [c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "a" "c"]],
    "name" "remc"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["coll"]],
    "name" "-uninitialized"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["pmap" "u" "s"]],
    "name" "unify-with-pmap"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c"]],
    "name" "-runnable?"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "a" "c"]],
    "name" "addc"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c" "id"]],
    "name" "-with-id"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c"]],
    "name" "-id"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x"]],
    "name" "root-var"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IAnswerCache",
    "line" 105,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IAnswerCache\n  (-add [this x])\n  (-cached? [this x]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IBuildTerm",
    "line" 61,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IBuildTerm\n  (build-term [u s]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IReifiableConstraint",
    "line" 174,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IReifiableConstraint\n  (-reifyc [c v r a]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "g"]],
    "name" "bind"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c"]],
    "name" "-entailed?"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "v"]],
    "name" "-reify-tabled"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["b" "gs" "c"]],
    "name" "ifa"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IWalkTerm",
    "line" 55,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IWalkTerm\n  (walk-term [v f]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this"]],
    "name" "toShortString"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "a" "x" "ws"]],
    "name" "constraints-for"}
   {"ns" "clojure.core.logic.protocols",
    "name" "ISuspendedStream",
    "line" 102,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol ISuspendedStream\n  (ready? [this]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IConstrainTree",
    "line" 227,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IConstrainTree\n  (-constrain-tree [t fc s]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.protocols",
    "name" "reifiable?",
    "column" 1,
    "line" 177,
    "source"
    "(defn reifiable? [x]\n  (instance? clojure.core.logic.protocols.IReifiableConstraint x))",
    "file" "clojure/core/logic/protocols.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["u" "s"]],
    "name" "build-term"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IFeature",
    "line" 233,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IFeature\n  (-feature [x]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "arity" "index"]],
    "name" "add-indexes"}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.protocols",
    "name" "enforceable?",
    "column" 1,
    "line" 182,
    "source"
    "(defn enforceable? [x]\n  (instance? clojure.core.logic.protocols.IEnforceableConstraint x))",
    "file" "clojure/core/logic/protocols.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["a"]],
    "name" "take*"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "arg" "ans"]],
    "name" "subunify"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x"]],
    "name" "-add"}
   {"ns" "clojure.core.logic.protocols",
    "name" "LConsSeq",
    "line" 35,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol LConsSeq\n  (lfirst [this])\n  (lnext [this]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"arglists" [["c" "id"]],
    "ns" "clojure.core.logic.protocols",
    "name" "with-id",
    "column" 1,
    "line" 162,
    "source"
    "(defn with-id [c id]\n  (if (instance? clojure.core.logic.protocols.IWithConstraintId c)\n    (-with-id c id)\n    (vary-meta c assoc ::id id)))",
    "file" "clojure/core/logic/protocols.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IRunnable",
    "line" 140,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IRunnable\n  (-runnable? [c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c"]],
    "name" "-rator"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IUnifyWithRecord",
    "line" 24,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IUnifyWithRecord\n  (unify-with-record [u v s]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.protocols",
    "name" "IPrefix",
    "line" 212,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IPrefix\n  (-prefix [c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["u" "v" "s" "cs"]],
    "name" "-disunify-terms"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IIfA",
    "line" 79,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IIfA\n  (ifa [b gs c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["v" "s"]],
    "name" "reify-term"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "a" "c"]],
    "name" "updatec"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["a" "f"]],
    "name" "mplus"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IConstraintOp",
    "line" 170,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol IConstraintOp\n  (-rator [c])\n  (-rands [c]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c"]],
    "name" "-unwrap"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "v"]],
    "name" "reify-tabled"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c" "v" "r" "a"]],
    "name" "-reifyc"}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.protocols",
    "name" "tree-constraint?",
    "column" 1,
    "line" 209,
    "source"
    "(defn tree-constraint? [x]\n  (instance? clojure.core.logic.protocols.ITreeConstraint x))",
    "file" "clojure/core/logic/protocols.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["a" "b"]],
    "name" "-merge-doms"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "x" "v"]],
    "name" "ext-run-cs"}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["c"]],
    "name" "-watched-stores"}
   {"ns" "clojure.core.logic.protocols",
    "name" "IUninitialized",
    "line" 12,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IUninitialized\n  (-uninitialized [coll]))",
    "file" "clojure/core/logic/protocols.clj",
    "arglists" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.protocols",
    "doc" nil,
    "arglists" [["this" "c" "state"]],
    "name" "runc"}],
  "clojure.core.logic.nominal"
  [{"arglists" [["lvar"]],
    "ns" "clojure.core.logic.nominal",
    "name" "nom",
    "column" 1,
    "line" 117,
    "source" "(defn nom [lvar]\n  (Nom. lvar))",
    "file" "clojure/core/logic/nominal.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.nominal",
    "doc" nil,
    "arglists" [["t" "swap" "s"]],
    "name" "swap-noms"}
   {"ns" "clojure.core.logic.nominal",
    "name" "INomSwap",
    "line" 21,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol INomSwap\n  (swap-noms [t swap s]))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" nil}
   {"arglists" [["v1" "v2" "swap"]],
    "ns" "clojure.core.logic.nominal",
    "name" "-suspc",
    "column" 1,
    "line" 214,
    "source" nil,
    "file" "clojure/core/logic/nominal.clj",
    "tag" nil}
   {"arglists" [["a" "swap"]],
    "ns" "clojure.core.logic.nominal",
    "name" "nom-swap",
    "column" 1,
    "line" 24,
    "source"
    "(defn nom-swap [a swap]\n  (cond\n    (= a (first swap)) (second swap)\n    (= a (second swap)) (first swap)\n    :else a))",
    "file" "clojure/core/logic/nominal.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.nominal",
    "name" "nom?",
    "column" 1,
    "line" 120,
    "source"
    "(defn nom? [x]\n  (instance? clojure.core.logic.nominal.Nom x))",
    "file" "clojure/core/logic/nominal.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.nominal",
    "name" "map->Tie",
    "line" 264,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.logic.nominal.Tie, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord Tie [binding-nom body]\n  ITreeTerm\n\n  IUnifyTerms\n  (unify-terms [v u s]\n    (cond\n      (tie? u)\n      (if (= (:binding-nom v) (:binding-nom u))\n        (unify s (:body v) (:body u))\n        (let [[t s] (swap-noms (:body v) [(:binding-nom v) (:binding-nom u)] s)]\n          ((composeg* \n            (hash (:binding-nom u) (:body v))\n            (== t (:body u))) s)))\n      :else nil))\n\n  IReifyTerm\n  (reify-term [v s]\n    (let [s (-reify* s binding-nom)]\n      (let [s (-reify* s body)]\n        s)))\n\n  IWalkTerm\n  (walk-term [v f]\n    (with-meta\n      (tie (walk-term (:binding-nom v) f)\n           (walk-term (:body v) f))\n      (meta v)))\n\n  IOccursCheckTerm\n  (occurs-check-term [v x s]\n    (occurs-check s x (:body v)))\n\n  IConstrainTree\n  (-constrain-tree [t fc s]\n    (fc (:body t) s))\n\n  IForceAnswerTerm\n  (-force-ans [v x]\n    (force-ans (:body v)))\n\n  INomSwap\n  (swap-noms [t swap s]\n    (let [[tbody s] (swap-noms (:body t) swap s)]\n      [(with-meta (tie (nom-swap (:binding-nom t) swap) tbody) (meta t)) s])))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.core.logic.nominal",
    "name" "fresh",
    "macro" true,
    "line" 130,
    "column" 1,
    "doc"
    "Creates fresh noms. Goals occuring within form a logical conjunction.",
    "tag" nil,
    "source"
    "(defmacro fresh\n  \"Creates fresh noms. Goals occuring within form a logical conjunction.\"\n  [[& noms] & goals]\n  `(fn [a#]\n     (-inc\n      (let [~@(nom-binds noms)]\n        (bind* a# ~@goals)))))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [[["&" "noms"] "&" "goals"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.nominal",
    "name" "tie?",
    "column" 1,
    "line" 312,
    "source"
    "(defn tie? [x]\n  (instance? clojure.core.logic.nominal.Tie x))",
    "file" "clojure/core/logic/nominal.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic.nominal",
    "name" "nom-binds",
    "line" 127,
    "column" 1,
    "tag" nil,
    "source" "(defn- nom-binds [syms]\n  (mapcat nom-bind syms))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [["syms"]]}
   {"arglists" [["binding-nom" "body"]],
    "ns" "clojure.core.logic.nominal",
    "name" "tie",
    "column" 1,
    "line" 309,
    "source"
    "(defn tie [binding-nom body]\n  (Tie. binding-nom body))",
    "file" "clojure/core/logic/nominal.clj",
    "tag" nil}
   {"arglists" [["a" "t"]],
    "ns" "clojure.core.logic.nominal",
    "name" "hash",
    "column" 1,
    "line" 192,
    "source" "(defn hash [a t]\n  (cgoal (-hash a t)))",
    "file" "clojure/core/logic/nominal.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.nominal",
    "name" "->Nom",
    "line" 81,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.nominal.Nom.",
    "tag" nil,
    "source"
    "(deftype Nom [lvar]\n  IBindable\n\n  Object\n  (toString [_]\n    (str \"<nom:\" (:name lvar) \">\"))\n  (hashCode [_]\n    (.hashCode lvar))\n  (equals [this o]\n    (and (.. this getClass (isInstance o))\n         (= lvar (:lvar o))))\n\n  clojure.lang.IObj\n  (withMeta [this new-meta]\n    (nom (with-meta lvar new-meta)))\n  (meta [this]\n    (meta lvar))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [_ k not-found]\n    (case k\n      :lvar lvar\n      :name (:name lvar)\n      :oname (:oname lvar)\n      not-found))\n\n  IReifyTerm\n  (reify-term [v s]\n    (ext s v (symbol (str (if (-> s meta (:reify-noms true)) \"a\" (:oname v)) \"_\" (count s)))))\n\n  INomSwap\n  (swap-noms [t swap s]\n    [(nom-swap t swap) s]))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [["lvar"]]}
   {"private" true,
    "ns" "clojure.core.logic.nominal",
    "name" "pprint-tie",
    "line" 321,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- pprint-tie [x]\n  (pp/pprint-logical-block\n    (.write ^Writer *out* \"[\")\n    (pp/write-out (:binding-nom x))\n    (.write ^Writer *out* \"] \")\n    (pp/write-out (:body x))))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [["x"]]}
   {"private" true,
    "ns" "clojure.core.logic.nominal",
    "name" "-do-suspc",
    "line" 198,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- -do-suspc [t1 t2 swap a]\n  (let [x (loop [vs #{t2} seen #{}]\n            (let [vs (clojure.set/difference vs seen)]\n              (cond\n                (empty? vs) true\n                (some #(occurs-check a % t1) vs) false\n                :else (recur\n                        (reduce\n                          (fn [s0 s1]\n                            (clojure.set/union s0 (:eset (root-val a s1))))\n                          #{} vs)\n                        (clojure.set/union vs seen)))))]\n    (when x\n      (let [[t1 a] (swap-noms t1 swap a)]\n        ((== t1 t2) a)))))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [["t1" "t2" "swap" "a"]]}
   {"private" true,
    "ns" "clojure.core.logic.nominal",
    "name" "-hash",
    "line" 146,
    "column" 1,
    "tag" nil,
    "source" nil,
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [["a" "x"]]}
   {"private" true,
    "ns" "clojure.core.logic.nominal",
    "name" "nom-bind",
    "line" 123,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- nom-bind [sym]\n  ((juxt identity\n         (fn [s] `(nom (lvar '~s)))) sym))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [["sym"]]}
   {"arglists" [["v1" "v2" "swap"]],
    "ns" "clojure.core.logic.nominal",
    "name" "suspc",
    "column" 1,
    "line" 256,
    "source"
    "(defn suspc [v1 v2 swap]\n  (cgoal (-suspc v1 v2 swap)))",
    "file" "clojure/core/logic/nominal.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.nominal",
    "name" "->Tie",
    "line" 264,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.nominal.Tie.",
    "tag" nil,
    "source"
    "(defrecord Tie [binding-nom body]\n  ITreeTerm\n\n  IUnifyTerms\n  (unify-terms [v u s]\n    (cond\n      (tie? u)\n      (if (= (:binding-nom v) (:binding-nom u))\n        (unify s (:body v) (:body u))\n        (let [[t s] (swap-noms (:body v) [(:binding-nom v) (:binding-nom u)] s)]\n          ((composeg* \n            (hash (:binding-nom u) (:body v))\n            (== t (:body u))) s)))\n      :else nil))\n\n  IReifyTerm\n  (reify-term [v s]\n    (let [s (-reify* s binding-nom)]\n      (let [s (-reify* s body)]\n        s)))\n\n  IWalkTerm\n  (walk-term [v f]\n    (with-meta\n      (tie (walk-term (:binding-nom v) f)\n           (walk-term (:body v) f))\n      (meta v)))\n\n  IOccursCheckTerm\n  (occurs-check-term [v x s]\n    (occurs-check s x (:body v)))\n\n  IConstrainTree\n  (-constrain-tree [t fc s]\n    (fc (:body t) s))\n\n  IForceAnswerTerm\n  (-force-ans [v x]\n    (force-ans (:body v)))\n\n  INomSwap\n  (swap-noms [t swap s]\n    (let [[tbody s] (swap-noms (:body t) swap s)]\n      [(with-meta (tie (nom-swap (:binding-nom t) swap) tbody) (meta t)) s])))",
    "file" "clojure/core/logic/nominal.clj",
    "arglists" [["binding-nom" "body"]]}],
  "cljs.core.logic.macros"
  [{"ns" "cljs.core.logic.macros",
    "name" "pred",
    "macro" true,
    "line" 161,
    "column" 1,
    "doc"
    "Check a predicate against the value logic var. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro pred\n  \"Check a predicate against the value logic var. Non-relational.\"\n  [v f]\n  `(project [~v]\n     (== (~f ~v) true)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["v" "f"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "all",
    "macro" true,
    "line" 107,
    "column" 1,
    "doc" "Like fresh but does does not create logic variables.",
    "tag" nil,
    "source"
    "(defmacro all\n  \"Like fresh but does does not create logic variables.\"\n  ([] `cljs.core.logic/s#)\n  ([& goals] `(fn [a#] (bind* a# ~@goals))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [[] ["&" "goals"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "==",
    "macro" true,
    "line" 40,
    "column" 1,
    "doc" "A goal that attempts to unify terms u and v.",
    "tag" nil,
    "source"
    "(defmacro ==\n  \"A goal that attempts to unify terms u and v.\"\n  [u v]\n  `(fn [a#]\n     (if-let [b# (cljs.core.logic/-unify a# ~u ~v)]\n       b# (cljs.core.logic/fail a#))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["u" "v"]]}
   {"arglists" [["&" "syms"]],
    "ns" "cljs.core.logic.macros",
    "name" "env-locals",
    "column" 1,
    "line" 362,
    "source"
    "(defn env-locals [& syms]\n  (disj (set (apply concat syms)) '_))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "name-with-attributes",
    "line" 323,
    "column" 1,
    "doc"
    "To be used in macro definitions.\n   Handles optional docstrings and attribute maps for a name to be defined\n   in a list of macro arguments. If the first macro argument is a string\n   it is added as a docstring to name and removed from the macro argument\n   list. If afterwards the first macro argument is a map, its entries are\n   added to the name's metadata map and the map is removed from the\n   macro argument list. The return value is a vector containing the name\n   with its extended metadata map and the list of unprocessed macro\n   arguments.",
    "tag" nil,
    "source"
    "(defn name-with-attributes\n  \"To be used in macro definitions.\n   Handles optional docstrings and attribute maps for a name to be defined\n   in a list of macro arguments. If the first macro argument is a string\n   it is added as a docstring to name and removed from the macro argument\n   list. If afterwards the first macro argument is a map, its entries are\n   added to the name's metadata map and the map is removed from the\n   macro argument list. The return value is a vector containing the name\n   with its extended metadata map and the list of unprocessed macro\n   arguments.\"\n  [name macro-args]\n  (let [[docstring macro-args] (if (string? (first macro-args))\n                                 [(first macro-args) (next macro-args)]\n                                 [nil macro-args])\n    [attr macro-args]          (if (map? (first macro-args))\n                                 [(first macro-args) (next macro-args)]\n                                 [{} macro-args])\n    attr                       (if docstring\n                                 (assoc attr :doc docstring)\n                                 attr)\n    attr                       (if (meta name)\n                                 (conj (meta name) attr)\n                                 attr)]\n    [(with-meta name attr) macro-args]))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["name" "macro-args"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "defne",
    "macro" true,
    "line" 392,
    "column" 1,
    "doc"
    "Define a goal fn. Supports pattern matching. All\n   patterns will be tried. See conde.",
    "tag" nil,
    "source"
    "(defmacro defne\n  \"Define a goal fn. Supports pattern matching. All\n   patterns will be tried. See conde.\"\n  [& rest]\n  `(defnm conde ~@rest))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "project",
    "macro" true,
    "line" 152,
    "column" 1,
    "doc"
    "Extract the values bound to the specified logic vars. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro project\n  \"Extract the values bound to the specified logic vars. Non-relational.\"\n  [[& vars] & goals]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (let [~@(project-bindings vars a)]\n         ((fresh []\n            ~@goals) ~a)))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [[["&" "vars"] "&" "goals"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "fna",
    "macro" true,
    "line" 411,
    "column" 1,
    "doc" "Define an anonymous soft cut goal. See conda.",
    "tag" nil,
    "source"
    "(defmacro fna\n  \"Define an anonymous soft cut goal. See conda.\"\n  [& rest]\n  `(fnm conda ~@rest))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "run*",
    "macro" true,
    "line" 81,
    "column" 1,
    "doc" "Executes goals until results are exhausted.",
    "tag" nil,
    "source"
    "(defmacro run*\n  \"Executes goals until results are exhausted.\"\n  [& goals]\n  `(run false ~@goals))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "goals"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "fnm",
    "macro" true,
    "line" 369,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro fnm\n   {:arglists '([t as tabled? & cs])}\n   [t as & cs]\n  (if-let [cs (and (= (first cs) :tabled) (rest cs))]\n    `(-fnm tabled ~t ~as ~@cs)\n    `(-fnm fn ~t ~as ~@cs)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["t" "as" "tabled?" "&" "cs"]]}
   {"arglists" [["p"]],
    "ns" "cljs.core.logic.macros",
    "name" "lcons-p?",
    "column" 1,
    "line" 231,
    "source"
    "(defn lcons-p? [p]\n  (and (coll? p)\n       (not (nil? (some '#{.} p)))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "conda",
    "macro" true,
    "line" 202,
    "column" 1,
    "doc"
    "Soft cut. Once the head of a clause has succeeded\n  all other clauses will be ignored. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro conda\n  \"Soft cut. Once the head of a clause has succeeded\n  all other clauses will be ignored. Non-relational.\"\n  [& clauses]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (ifa* ~@(map (cond-clauses a) clauses)))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "clauses"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "fnu",
    "macro" true,
    "line" 416,
    "column" 1,
    "doc" "Define an anonymous committed choice goal. See condu.",
    "tag" nil,
    "source"
    "(defmacro fnu\n  \"Define an anonymous committed choice goal. See condu.\"\n  [& rest]\n  `(fnm condu ~@rest))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "is",
    "macro" true,
    "line" 167,
    "column" 1,
    "doc"
    "Set the value of a var to value of another var with the operation\n   applied. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro is\n  \"Set the value of a var to value of another var with the operation\n   applied. Non-relational.\"\n  [u v op]\n  `(project [~v]\n     (== ~u (~op ~v))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["u" "v" "op"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "matche",
    "macro" true,
    "line" 398,
    "column" 1,
    "doc"
    "Pattern matching macro. All patterns will be tried.\n  See conde.",
    "tag" nil,
    "source"
    "(defmacro matche\n  \"Pattern matching macro. All patterns will be tried.\n  See conde.\"\n  [xs & cs]\n  (binding [*locals* (env-locals xs (-> &env :locals keys))]\n    (handle-clauses `conde xs cs)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["xs" "&" "cs"]]}
   {"arglists" [["s"]],
    "ns" "cljs.core.logic.macros",
    "name" "lvar-sym?",
    "column" 1,
    "line" 255,
    "source"
    "(defn lvar-sym? [s]\n  (and (symbol? s)\n       (not= s '.)\n       (not (contains? *locals* s))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"arglists" [["p"]],
    "ns" "cljs.core.logic.macros",
    "name" "all-blank?",
    "column" 1,
    "line" 301,
    "source" "(defn all-blank? [p]\n  (every? #(= % '_) p))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "lvaro",
    "macro" true,
    "line" 348,
    "column" 1,
    "doc"
    "Goal to test whether a logic var is ground. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro lvaro\n  \"Goal to test whether a logic var is ground. Non-relational.\"\n  [v]\n  `(fn [a#]\n     (if (cljs.core.logic/lvar? (cljs.core.logic/-walk a# ~v))\n       a# nil)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["v"]]}
   {"arglists" [["a" "lvar"]],
    "ns" "cljs.core.logic.macros",
    "name" "trace-lvar",
    "column" 1,
    "line" 127,
    "source"
    "(defn trace-lvar [a lvar]\n  `(println (format \"%5s = %s\" (str '~lvar) (-reify ~a ~lvar))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "fresh",
    "macro" true,
    "line" 57,
    "column" 1,
    "doc"
    "Creates fresh variables. Goals occuring within form a logical \n  conjunction.",
    "tag" nil,
    "source"
    "(defmacro fresh\n  \"Creates fresh variables. Goals occuring within form a logical \n  conjunction.\"\n  [[& lvars] & goals]\n  `(fn [a#]\n     (-inc\n      (let [~@(lvar-binds lvars)]\n        (bind* a# ~@goals)))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [[["&" "lvars"] "&" "goals"]]}
   {"arglists" [["vars" "s"]],
    "ns" "cljs.core.logic.macros",
    "name" "project-bindings",
    "column" 1,
    "line" 149,
    "source"
    "(defn project-bindings [vars s]\n  (reduce concat (map (project-binding s) vars)))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "ifa*",
    "macro" true,
    "line" 182,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ifa*\n  ([])\n  ([[e & gs] & grest]\n     `(cljs.core.logic/-ifa ~e [~@gs]\n        ~(if (seq grest)\n           `(delay (ifa* ~@grest))\n           nil))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [[] [["e" "&" "gs"] "&" "grest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "-inc",
    "macro" true,
    "line" 37,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro -inc [& rest]\n  `(cljs.core.logic/Inc. (fn [] ~@rest)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "trace-lvars",
    "macro" true,
    "line" 130,
    "column" 1,
    "doc" "Goal for tracing the values of logic variables.",
    "tag" nil,
    "source"
    "(defmacro trace-lvars\n  \"Goal for tracing the values of logic variables.\"\n  [title & lvars]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (println ~title)\n       ~@(map (partial trace-lvar a) lvars)\n       ~a)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["title" "&" "lvars"]]}
   {"arglists" [["syms"]],
    "ns" "cljs.core.logic.macros",
    "name" "lvar-binds",
    "column" 1,
    "line" 24,
    "source" "(defn lvar-binds [syms]\n  (mapcat lvar-bind syms))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "log",
    "macro" true,
    "line" 115,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro log [& s]\n  \"Goal for println\"\n  `(fn [a#]\n     (println ~@s)\n     a#))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "s"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "condu",
    "macro" true,
    "line" 210,
    "column" 1,
    "doc"
    "Committed choice. Once the head (first goal) of a clause \n  has succeeded, remaining goals of the clause will only\n  be run once. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro condu\n  \"Committed choice. Once the head (first goal) of a clause \n  has succeeded, remaining goals of the clause will only\n  be run once. Non-relational.\"\n  [& clauses]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (ifu* ~@(map (cond-clauses a) clauses)))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "clauses"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "defna",
    "macro" true,
    "line" 421,
    "column" 1,
    "doc" "Define a soft cut goal. See conda.",
    "tag" nil,
    "source"
    "(defmacro defna\n  \"Define a soft cut goal. See conda.\"\n  [& rest]\n  `(defnm conda ~@rest))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "rest"]]}
   {"arglists" [["p"] ["p" "seen"]],
    "ns" "cljs.core.logic.macros",
    "name" "extract-vars",
    "column" 1,
    "line" 260,
    "source"
    "(defn extract-vars\n  ([p]\n     (set (cond\n           (lvar-sym? p) [p]           \n           (coll? p) (let [p (if (seq? p) (rest p) p)]\n                       (filter lvar-sym? (flatten p)))\n           :else nil)))\n  ([p seen]\n     (set/difference (extract-vars p) (set seen))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "defnu",
    "macro" true,
    "line" 426,
    "column" 1,
    "doc" "Define a committed choice goal. See condu.",
    "tag" nil,
    "source"
    "(defmacro defnu\n  \"Define a committed choice goal. See condu.\"\n  [& rest]\n  `(defnm condu ~@rest))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "run",
    "macro" true,
    "line" 76,
    "column" 1,
    "doc" "Executes goals until a maximum of n results are found.",
    "tag" nil,
    "source"
    "(defmacro run\n  \"Executes goals until a maximum of n results are found.\"\n  [n & goals]\n  `(doall (solve ~n ~@goals)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["n" "&" "goals"]]}
   {"arglists" [["cs"]],
    "ns" "cljs.core.logic.macros",
    "name" "fresh-expr?",
    "column" 1,
    "line" 270,
    "source" "(defn fresh-expr? [cs]\n  (= (first cs) `fresh))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"private" true,
    "ns" "cljs.core.logic.macros",
    "name" "p->term",
    "line" 240,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- p->term [p]\n  (cond\n   (= p '_) `(cljs.core.logic/lvar)\n   (lcons-p? p) (p->llist p)\n   (and (coll? p) (not= (first p) 'quote))\n     (cond\n      ;; support simple expressions\n      (list? p) p\n      ;; preserve original collection type\n      :else (let [ps (map p->term p)]\n              (cond\n               (instance? clojure.lang.MapEntry p) (into [] ps)\n               :else (into (empty p) ps))))\n   :else p))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["p"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "llist",
    "macro" true,
    "line" 7,
    "column" 1,
    "doc"
    "Constructs a sequence from 2 or more arguments, with the last argument as the tail.\n  The tail is improper if the last argument is a logic variable.",
    "tag" nil,
    "source"
    "(defmacro llist\n  \"Constructs a sequence from 2 or more arguments, with the last argument as the tail.\n  The tail is improper if the last argument is a logic variable.\"\n  ([f s] `(cljs.core.logic/lcons ~f ~s))\n  ([f s & rest] `(cljs.core.logic/lcons ~f (llist ~s ~@rest))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["f" "s"] ["f" "s" "&" "rest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "bind*",
    "macro" true,
    "line" 27,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro bind*\n  ([a g] `(cljs.core.logic/-bind ~a ~g))\n  ([a g & g-rest]\n     `(bind* (cljs.core.logic/-bind ~a ~g) ~@g-rest)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["a" "g"] ["a" "g" "&" "g-rest"]]}
   {"arglists" [["a" "clauses"]],
    "ns" "cljs.core.logic.macros",
    "name" "bind-conde-clauses",
    "column" 1,
    "line" 17,
    "source"
    "(defn bind-conde-clauses [a clauses]\n  (map (bind-conde-clause a) clauses))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "run-nc",
    "macro" true,
    "line" 86,
    "column" 1,
    "doc"
    "Executes goals until a maximum of n results are found. Does not occurs-check.",
    "tag" nil,
    "source"
    "(defmacro run-nc\n  \"Executes goals until a maximum of n results are found. Does not occurs-check.\"\n  [& [n & goals]]\n  `(binding [*occurs-check* false]\n     (run ~n ~@goals)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" ["n" "&" "goals"]]]}
   {"arglists" [["as"]],
    "ns" "cljs.core.logic.macros",
    "name" "handle-clause",
    "column" 1,
    "line" 304,
    "source"
    "(defn handle-clause [as]\n  (when-not (vector? as)\n    (throw (Exception. (str \"Expecting vector of arguments, instead \" as))))\n  (fn [[p & exprs]]\n    (when-not (vector? p)\n      (throw (Exception. (str \"Expecting vector of matches, instead \" p))))\n    (when-not (= (count p) (count as))\n      (warn \"Differing number of matches. Matching\" p \"against\" as))\n    (let [pas (partition 2 (interleave p as))\n          r (ex* pas exprs #{})]\n      (if (all-blank? p)\n        r\n        (list r)))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"arglists" [["a"]],
    "ns" "cljs.core.logic.macros",
    "name" "bind-conde-clause",
    "column" 1,
    "line" 13,
    "source"
    "(defn bind-conde-clause [a]\n  (fn [g-rest]\n    `(bind* ~a ~@g-rest)))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"arglists" [["p"]],
    "ns" "cljs.core.logic.macros",
    "name" "p->llist",
    "column" 1,
    "line" 235,
    "source"
    "(defn p->llist [p]\n  `(llist\n    ~@(map p->term\n           (remove #(contains? '#{.} %) p))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"arglists" [["sym"]],
    "ns" "cljs.core.logic.macros",
    "name" "lvar-bind",
    "column" 1,
    "line" 20,
    "source"
    "(defn lvar-bind [sym]\n  ((juxt identity\n         (fn [s] `(cljs.core.logic/lvar '~s))) sym))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "matcha",
    "macro" true,
    "line" 431,
    "column" 1,
    "doc" "Define a soft cut pattern match. See conda.",
    "tag" nil,
    "source"
    "(defmacro matcha\n  \"Define a soft cut pattern match. See conda.\"\n  [xs & cs]\n  (binding [*locals* (env-locals xs (-> &env :locals keys))]\n    (handle-clauses `conda xs cs)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["xs" "&" "cs"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "nonlvaro",
    "macro" true,
    "line" 355,
    "column" 1,
    "doc"
    "Goal to test whether a logic var is ground. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro nonlvaro\n  \"Goal to test whether a logic var is ground. Non-relational.\"\n  [v]\n  `(fn [a#]\n     (if (not (cljs.core.logic/lvar? (cljs.core.logic/walk a# ~v)))\n       a# nil)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["v"]]}
   {"arglists" [["vs" "t" "a"] ["vs" "t" "a" "exprs"]],
    "ns" "cljs.core.logic.macros",
    "name" "ex",
    "column" 1,
    "line" 273,
    "source"
    "(defn ex\n  ([vs t a]\n     `(fresh [~@vs]\n        (== ~t ~a)))\n  ([vs t a exprs]\n     (if (fresh-expr? exprs)\n       `(fresh [~@vs]\n          (== ~t ~a)\n          ~exprs)\n       `(fresh [~@vs]\n          (== ~t ~a)\n          ~@exprs))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "ifu*",
    "macro" true,
    "line" 190,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ifu*\n  ([])\n  ([[e & gs] & grest]\n     `(cljs.core.logic/-ifu ~e [~@gs]\n        ~(if (seq grest)\n           `(delay (ifu* ~@grest))\n           nil))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [[] [["e" "&" "gs"] "&" "grest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "lazy-run",
    "macro" true,
    "line" 97,
    "column" 1,
    "doc"
    "Lazily executes goals until a maximum of n results are found.",
    "tag" nil,
    "source"
    "(defmacro lazy-run\n  \"Lazily executes goals until a maximum of n results are found.\"\n  [& [n & goals]]\n  `(solve ~n ~@goals))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" ["n" "&" "goals"]]]}
   {"ns" "cljs.core.logic.macros",
    "name" "-fnm",
    "macro" true,
    "line" 365,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro -fnm [fn-gen t as & cs]\n  (binding [*locals* (env-locals as (keys &env))]\n     `(~fn-gen [~@as] ~(handle-clauses t as cs))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["fn-gen" "t" "as" "&" "cs"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "*locals*",
    "line" 5,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true} *locals*)",
    "file" "cljs/core/logic/macros.clj",
    "dynamic" true,
    "arglists" nil}
   {"arglists" [["t" "as" "cs"]],
    "ns" "cljs.core.logic.macros",
    "name" "handle-clauses",
    "column" 1,
    "line" 318,
    "source"
    "(defn handle-clauses [t as cs]\n  `(~t\n    ~@(doall (map (handle-clause as) cs))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "solve",
    "macro" true,
    "line" 66,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro solve [& [n [x] & goals]]\n  `(let [xs# (cljs.core.logic/-take* (-inc\n                      ((fresh [~x] ~@goals\n                         (fn [a#]\n                           (cons (cljs.core.logic/-reify a# ~x) '()))) ;; TODO: do we need this?\n                       cljs.core.logic/empty-s)))]\n     (if ~n\n       (take ~n xs#)\n       xs#)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" ["n" ["x"] "&" "goals"]]]}
   {"ns" "cljs.core.logic.macros",
    "name" "trace-s",
    "macro" true,
    "line" 121,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro trace-s []\n  \"Goal that prints the current substitution\"\n  `(fn [a#]\n     (println (str a#))\n     a#))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [[]]}
   {"arglists" [[[["p" "a" "as" "pa"] "&" "par"] "exprs" "seen"]],
    "ns" "cljs.core.logic.macros",
    "name" "ex*",
    "column" 1,
    "line" 286,
    "source"
    "(defn ex* [[[p a :as pa] & par] exprs seen]\n  (let [t (p->term p)\n        vs (extract-vars p seen)\n        seen (reduce conj seen vs)]\n    (cond\n     (nil? pa) exprs\n     (= p '_) (ex* par exprs seen)\n     (empty? par) (if exprs\n                    (ex vs t a exprs)\n                    (ex vs t a))\n     :else (let [r (ex* par exprs seen)]\n             (if r\n               (ex vs t a r)\n               (ex vs t a))))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "conde",
    "macro" true,
    "line" 47,
    "column" 1,
    "doc"
    "Logical disjunction of the clauses. The first goal in\n  a clause is considered the head of that clause. Interleaves the\n  execution of the clauses.",
    "tag" nil,
    "source"
    "(defmacro conde\n  \"Logical disjunction of the clauses. The first goal in\n  a clause is considered the head of that clause. Interleaves the\n  execution of the clauses.\"\n  [& clauses]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (-inc\n        (mplus* ~@(bind-conde-clauses a clauses))))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "clauses"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "mplus*",
    "macro" true,
    "line" 32,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro mplus*\n  ([e] e)\n  ([e & e-rest]\n     `(cljs.core.logic/-mplus ~e (-inc (mplus* ~@e-rest)))))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["e"] ["e" "&" "e-rest"]]}
   {"arglists" [["s"]],
    "ns" "cljs.core.logic.macros",
    "name" "project-binding",
    "column" 1,
    "line" 145,
    "source"
    "(defn project-binding [s]\n  (fn [var]\n    `(~var (cljs.core.logic/-walk* ~s ~var))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"arglists" [["&" "msg"]],
    "ns" "cljs.core.logic.macros",
    "name" "warn",
    "column" 1,
    "line" 225,
    "source"
    "(defn warn [& msg]\n  (binding [*out* *err*]\n    (apply println \"WARNING:\" msg)))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "fne",
    "macro" true,
    "line" 386,
    "column" 1,
    "doc"
    "Define an anonymous goal fn. Supports pattern matching. All\n   patterns will be tried. See conde.",
    "tag" nil,
    "source"
    "(defmacro fne\n  \"Define an anonymous goal fn. Supports pattern matching. All\n   patterns will be tried. See conde.\"\n  [& rest]\n  `(fnm conde ~@rest))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "matchu",
    "macro" true,
    "line" 437,
    "column" 1,
    "doc" "Define a committed choice goal. See condu.",
    "tag" nil,
    "source"
    "(defmacro matchu\n  \"Define a committed choice goal. See condu.\"\n  [xs & cs]\n  (binding [*locals* (env-locals xs (-> &env :locals keys))]\n    (handle-clauses `condu xs cs)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["xs" "&" "cs"]]}
   {"arglists" [["a"]],
    "ns" "cljs.core.logic.macros",
    "name" "cond-clauses",
    "column" 1,
    "line" 198,
    "source"
    "(defn cond-clauses [a]\n  (fn [goals]\n    `((~(first goals) ~a) ~@(rest goals))))",
    "file" "cljs/core/logic/macros.clj",
    "tag" nil}
   {"ns" "cljs.core.logic.macros",
    "name" "defnm",
    "macro" true,
    "line" 376,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro defnm [t n & rest]\n  (let [[n [as & cs]] (name-with-attributes n rest)\n        e (if (-> n meta :tabled)\n            `(fnm ~t ~as :tabled ~@cs)\n            `(fnm ~t ~as ~@cs))]\n    `(def ~n ~e)))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["t" "n" "&" "rest"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "run-nc*",
    "macro" true,
    "line" 92,
    "column" 1,
    "doc"
    "Executes goals until results are exhausted. Does not occurs-check.",
    "tag" nil,
    "source"
    "(defmacro run-nc*\n  \"Executes goals until results are exhausted. Does not occurs-check.\"\n  [& goals]\n  `(run-nc false ~@goals))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "goals"]]}
   {"ns" "cljs.core.logic.macros",
    "name" "lazy-run*",
    "macro" true,
    "line" 102,
    "column" 1,
    "doc" "Lazily executes goals until results are exhausted.",
    "tag" nil,
    "source"
    "(defmacro lazy-run*\n  \"Lazily executes goals until results are exhausted.\"\n  [& goals]\n  `(solve false ~@goals))",
    "file" "cljs/core/logic/macros.clj",
    "arglists" [["&" "goals"]]}],
  "clojure.core.logic.unifier"
  [{"arglists" [["opts" "s"]],
    "ns" "clojure.core.logic.unifier",
    "name" "init-s",
    "column" 1,
    "line" 103,
    "source"
    "(defn init-s [opts s]\n  (let [s (reduce (fn [s [k v]] ((== k v) s)) s (:as opts))]\n    (reduce queue-constraints\n      (with-meta s {:reify-vars (fn [v rs] rs)})\n      (:when opts))))",
    "file" "clojure/core/logic/unifier.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.unifier",
    "name" "unifier",
    "line" 148,
    "column" 1,
    "doc" "Return the unifier for terms ts. Will prep the terms.",
    "tag" nil,
    "source"
    "(defn unifier\n  \"Return the unifier for terms ts. Will prep the terms.\"\n  ([ts] (unifier {} ts))\n  ([opts ts]\n     (let [opts (if (contains? opts :as)\n                  (assoc opts :as\n                    (->> (:as opts)\n                      (map (fn [[k v]] [(lvar k false) (prep v)]))\n                      (into {})))\n                  opts)\n           ts' (map prep ts)\n           lvars (->> (concat ts' (map val (:as opts)))\n                   (map #(-> % meta ::lvars))\n                   (reduce into))]\n       (unifier* (assoc opts :lvars lvars) (map prep ts)))))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["ts"] ["opts" "ts"]]}
   {"arglists" [["s" "c" "vs"]],
    "ns" "clojure.core.logic.unifier",
    "name" "queue-constraint",
    "column" 1,
    "line" 71,
    "source"
    "(defn queue-constraint [s c vs]\n  (cond\n    (vector? vs)\n    (queue s (-unwrap (apply c (map #(lvar % false) vs))))\n\n    (set? vs)\n    (reduce (fn [s v] (queue s (-unwrap (c (lvar v false))))) s vs)\n\n    (symbol? vs)\n    (queue s (-unwrap (apply c (map #(lvar % false) (list vs)))))\n\n    :else\n    (throw\n     (Exception.\n      (str \"Only symbol, set of symbols, or vector of symbols allowed \"\n           \"on left hand side\")))))",
    "file" "clojure/core/logic/unifier.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.unifier",
    "name" "prep",
    "line" 55,
    "column" 1,
    "doc"
    "Prep a quoted expression. All symbols preceded by ? will\n  be replaced with logic vars.",
    "tag" nil,
    "source"
    "(defn prep\n  \"Prep a quoted expression. All symbols preceded by ? will\n  be replaced with logic vars.\"\n  [expr]\n  (let [lvars (atom {})\n        prepped (cond\n                  (lvarq-sym? expr) (proc-lvar expr lvars)\n\n                  (lcons-expr? expr)\n                  (prep* expr lvars true)\n\n                  :else (walk-term expr (replace-lvar lvars)))]\n    (if (instance? clojure.lang.IMeta prepped)\n      (with-meta prepped {::lvars (keys @lvars)})\n      prepped)))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["expr"]]}
   {"ns" "clojure.core.logic.unifier",
    "name" "unify",
    "line" 135,
    "column" 1,
    "doc"
    "Unify the terms ts returning a the value that represents their\n   unificaiton. Will prep the terms.",
    "tag" nil,
    "source"
    "(defn unify\n  \"Unify the terms ts returning a the value that represents their\n   unificaiton. Will prep the terms.\"\n  ([ts] (unify {} ts))\n  ([opts ts]\n     (let [opts (if (contains? opts :as)\n                  (assoc opts :as\n                    (->> (:as opts)\n                      (map (fn [[k v]] [(lvar k false) (prep v)]))\n                      (into {})))\n                  opts)]\n       (unify* opts (map prep ts)))))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["ts"] ["opts" "ts"]]}
   {"private" true,
    "ns" "clojure.core.logic.unifier",
    "name" "lvarq-sym?",
    "line" 9,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- lvarq-sym? [s]\n  (and (symbol? s) (= (first (str s)) \\?)))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["s"]]}
   {"ns" "clojure.core.logic.unifier",
    "name" "unify*",
    "line" 109,
    "column" 1,
    "doc" "Unify the terms ts.",
    "tag" nil,
    "source"
    "(defn unify*\n  \"Unify the terms ts.\"\n  ([ts] (unify* {} ts))\n  ([opts ts]\n     (let [init-s (init-s opts empty-s)]\n       (-unify*\n         (vary-meta init-s assoc :reify-vars false)\n         (reduce #(-unify* init-s %1 %2) (butlast ts))\n         (last ts)))))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["ts"] ["opts" "ts"]]}
   {"arglists" [["s" ["vs" "cs"]]],
    "ns" "clojure.core.logic.unifier",
    "name" "queue-constraints",
    "column" 1,
    "line" 88,
    "source"
    "(defn queue-constraints [s [vs cs]]\n  (let [cs (if-not (sequential? cs) [cs] cs)]\n    (reduce (fn [s c] (queue-constraint s c vs)) s cs)))",
    "file" "clojure/core/logic/unifier.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.unifier",
    "name" "unifier*",
    "line" 119,
    "column" 1,
    "doc"
    "Return the unifier that unifies terms ts.\n  All terms in ts should prepped terms.",
    "tag" nil,
    "source"
    "(defn unifier*\n  \"Return the unifier that unifies terms ts.\n  All terms in ts should prepped terms.\"\n  ([ts] (unifier* {} ts))\n  ([opts ts]\n     (letfn [(-unifier* [s u w]\n               (let [s (fix-constraints (l/unify (with-meta s {:reify-vars false}) u w))]\n                 (when s\n                   (->> (:lvars opts)\n                     (map (fn [sym] [sym (lvar sym false)]))\n                     (filter (fn [[sym var]] (not= (walk s var) var)))   \n                     (map (fn [[sym var]] [sym (-reify s var)]))\n                     (into {})))))]\n       (let [init-s (init-s opts empty-s)]\n         (reduce #(-unifier* init-s %1 %2) ts)))))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["ts"] ["opts" "ts"]]}
   {"private" true,
    "ns" "clojure.core.logic.unifier",
    "name" "replace-lvar",
    "line" 24,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- replace-lvar [store]\n  (fn [expr]\n    (if (lvarq-sym? expr)\n      (proc-lvar expr store)\n      (if (lcons-expr? expr)\n        (prep* expr store)\n        expr))))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["store"]]}
   {"arglists" [["init-s" "u" "w"]],
    "ns" "clojure.core.logic.unifier",
    "name" "-unify*",
    "column" 1,
    "line" 92,
    "source"
    "(defn -unify* [init-s u w]\n  (first\n    (take*\n      (fn []\n        ((fresh [q]\n           (== u w) (== q u)\n           (fn [a]\n             (fix-constraints a))\n           (reifyg q))\n         init-s)))))",
    "file" "clojure/core/logic/unifier.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic.unifier",
    "name" "prep*",
    "line" 32,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- prep*\n  ([expr store] (prep* expr store false false))\n  ([expr store lcons?] (prep* expr store lcons? false))\n  ([expr store lcons? last?]\n     (let [expr (if (and last? (seq expr))\n                  (first expr)\n                  expr)]\n       (cond\n         (lvarq-sym? expr)\n         (proc-lvar expr store)\n        \n         (seq? expr)\n         (if (or lcons? (lcons-expr? expr))\n           (let [[f & n] expr\n                 skip (= f '.)\n                 tail (prep* n store lcons? skip)]\n             (if skip\n               tail\n               (lcons (prep* f store) tail)))\n           (walk-term expr (replace-lvar store)))\n         \n        :else expr))))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists"
    [["expr" "store"]
     ["expr" "store" "lcons?"]
     ["expr" "store" "lcons?" "last?"]]}
   {"private" true,
    "ns" "clojure.core.logic.unifier",
    "name" "lcons-expr?",
    "line" 19,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- lcons-expr? [expr]\n  (and (seq? expr) (some '#{.} (set expr))))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["expr"]]}
   {"private" true,
    "ns" "clojure.core.logic.unifier",
    "name" "proc-lvar",
    "line" 12,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- proc-lvar [lvar-expr store]\n  (let [v (if-let [u (@store lvar-expr)]\n            u\n            (lvar lvar-expr false))]\n    (swap! store assoc lvar-expr v)\n    v))",
    "file" "clojure/core/logic/unifier.clj",
    "arglists" [["lvar-expr" "store"]]}],
  "clojure.core.logic.fd"
  [{"arglists" nil,
    "ns" "clojure.core.logic.fd",
    "name" "binops",
    "column" 1,
    "line" 1108,
    "source" "(def binops (set (keys binops->fd)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["a" "x"]],
    "ns" "clojure.core.logic.fd",
    "name" "get-dom",
    "column" 1,
    "line" 588,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this" "that"]],
    "name" "-disjoint?"}
   {"ns" "clojure.core.logic.fd",
    "name" "==",
    "line" 746,
    "column" 1,
    "doc"
    "A finite domain constraint. u and v must be equal. u and v must\n   eventually be given domains if vars.",
    "tag" nil,
    "source"
    "(defn ==\n  \"A finite domain constraint. u and v must be equal. u and v must\n   eventually be given domains if vars.\"\n  [u v]\n  (cgoal (==c u v)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["u" "v"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "ISortedDomain",
    "line" 23,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol ISortedDomain\n  (-drop-one [this])\n  (-drop-before [this n])\n  (-keep-before [this n]))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "distinct",
    "line" 1074,
    "column" 1,
    "doc"
    "A finite domain constraint that will guarantee that \n   all vars that occur in v* will be unified with unique \n   values. v* need not be ground. Any vars in v* should\n   eventually be given a domain.",
    "tag" nil,
    "source"
    "(defn distinct\n  \"A finite domain constraint that will guarantee that \n   all vars that occur in v* will be unified with unique \n   values. v* need not be ground. Any vars in v* should\n   eventually be given a domain.\"\n  [v*]\n  (cgoal (distinctc v*)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["v*"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.fd",
    "name" "interval?",
    "column" 1,
    "line" 350,
    "source" "(defn interval? [x]\n  (instance? IntervalFD x))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["form"]],
    "ns" "clojure.core.logic.fd",
    "name" "expand",
    "column" 1,
    "line" 1110,
    "source"
    "(defn expand [form]\n  (if (seq? form)\n    (let [[op & args] form]\n     (if (and (binops op) (core/> (count args) 2))\n       (list op (expand (first args))\n             (expand (cons op (rest args))))\n       (cons op (map expand args))))\n    form))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["u" "v"]],
    "ns" "clojure.core.logic.fd",
    "name" "!=c",
    "column" 1,
    "line" 752,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "->FiniteDomain",
    "line" 64,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.fd.FiniteDomain.",
    "tag" nil,
    "source"
    "(deftype FiniteDomain [s min max]\n  Object\n  (equals [this that]\n    (if (finite-domain? that)\n      (if (= (-member-count this) (-member-count that))\n        (= s (:s that))\n        false)\n      false))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :s s\n      :min min\n      :max max\n      not-found))\n\n  IMemberCount\n  (-member-count [this] (count s))\n\n  IInterval\n  (-lb [_] min)\n  (-ub [_] max)\n\n  ISortedDomain\n  (-drop-one [_]\n    (let [s (disj s min)\n          c (count s)]\n      (cond\n       (= c 1) (first s)\n       (core/> c 1) (FiniteDomain. s (first s) max)\n       :else nil)))\n\n  (-drop-before [_ n]\n    (apply domain (drop-while #(core/< % n) s)))\n\n  (-keep-before [this n]\n    (apply domain (take-while #(core/< % n) s)))\n\n  ISet\n  (-member? [this n]\n    (if (s n) true false))\n\n  (-disjoint? [this that]\n    (cond\n     (integer? that)\n       (if (s that) false true)\n     (instance? FiniteDomain that)\n       (cond\n         (core/< max (:min that)) true\n         (core/> min (:max that)) true\n         :else (empty? (set/intersection s (:s that))))\n     :else (disjoint?* this that)))\n\n  (-intersection [this that]\n    (cond\n     (integer? that)\n       (when (-member? this that) that)\n     (instance? FiniteDomain that)\n       (sorted-set->domain (set/intersection s (:s that)))\n     :else\n       (intersection* this that)))\n\n  (-difference [this that]\n    (cond\n     (integer? that)\n       (sorted-set->domain (disj s that))\n     (instance? FiniteDomain that)\n       (sorted-set->domain (set/difference s (:s that)))\n     :else\n       (difference* this that)))\n\n  IIntervals\n  (-intervals [_] (seq s))\n\n  IMergeDomains\n  (-merge-doms [this that]\n    (-intersection this that)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["s" "min" "max"]]}
   {"arglists" [["form" "vars"] ["form" "vars" "out"]],
    "ns" "clojure.core.logic.fd",
    "name" "eq*",
    "column" 1,
    "line" 1119,
    "source"
    "(defn eq*\n  ([form vars] (eq* form vars nil))\n  ([form vars out]\n     (if (seq? form)\n       (let [[op r1 r2] form\n             [outl outlv?] (if (seq? r1)\n                             (let [s (gensym)]\n                               (swap! vars conj s)\n                               [s true])\n                             [r1 false])\n             [outr outrv?] (if (seq? r2)\n                             (let [s (gensym)]\n                               (swap! vars conj s)\n                               [s true])\n                             [r2 false])\n             op (binops->fd op)]\n         (cons (if out\n                 (list op outl outr out)\n                 (list op outl outr))\n               (concat (when (seq? r1)\n                         (eq* r1 vars (when outlv? outl)))\n                       (when (seq? r2)\n                         (eq* r2 vars (when outrv? outr))))))\n       form)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["pred" "ls"]],
    "ns" "clojure.core.logic.fd",
    "name" "list-sorted?",
    "column" 1,
    "line" 1028,
    "source"
    "(defn list-sorted? [pred ls]\n  (if (empty? ls)\n    true\n    (loop [f (first ls) ls (next ls)]\n      (if ls\n        (let [s (first ls)]\n         (if (pred f s)\n           (recur s (next ls))\n           false))\n        true))))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["u" "v" "w"]],
    "ns" "clojure.core.logic.fd",
    "name" "+c",
    "column" 1,
    "line" 846,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this" "n"]],
    "name" "-member?"}
   {"ns" "clojure.core.logic.fd",
    "name" "in",
    "macro" true,
    "line" 643,
    "column" 1,
    "doc" "Assign vars to domain. The domain must come last.",
    "tag" nil,
    "source"
    "(defmacro in\n  \"Assign vars to domain. The domain must come last.\"\n  [& xs-and-dom]\n  (let [xs (butlast xs-and-dom)\n        dom (last xs-and-dom)\n        domsym (gensym \"dom_\")]\n    `(let [~domsym ~dom]\n      (fresh []\n        ~@(map (fn [x]\n                 `(dom ~x ~domsym))\n               xs)))))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["&" "xs-and-dom"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "+",
    "line" 897,
    "column" 1,
    "doc"
    "A finite domain constraint for addition and subtraction.\n   u, v & w must eventually be given domains if vars.",
    "tag" nil,
    "source"
    "(defn +\n  \"A finite domain constraint for addition and subtraction.\n   u, v & w must eventually be given domains if vars.\"\n  [u v w]\n  (cgoal (+c u v w)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["u" "v" "w"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this"]],
    "name" "-intervals"}
   {"arglists" [["is" "js"]],
    "ns" "clojure.core.logic.fd",
    "name" "intersection*",
    "column" 1,
    "line" 366,
    "source"
    "(defn intersection* [is js]\n  (loop [is (seq (-intervals is)) js (seq (-intervals js)) r []]\n    (if (and is js)\n      (let [i (first is)\n            j (first js)]\n        (cond\n         (interval-< i j) (recur (next is) js r)\n         (interval-> i j) (recur is (next js) r)\n         :else\n         (let [[imin imax] (bounds i)\n               [jmin jmax] (bounds j)]\n           (cond\n            (core/<= imin jmin)\n            (cond\n             (core/< imax jmax)\n             (recur (next is)\n                    (cons (interval (inc imax) jmax) (next js))\n                    (conj r (interval jmin imax)))\n             (core/> imax jmax)\n             (recur (cons (interval (inc jmax) imax) (next is))\n                    (next js)\n                    (conj r j))\n             :else\n             (recur (next is) (next js)\n                    (conj r (interval jmin jmax))))\n            (core/> imin jmin)\n            (cond\n             (core/> imax jmax)\n             (recur (cons (interval (inc jmax) imax) (next is))\n                    (next js)\n                    (conj r (interval imin jmax)))\n             (core/< imax jmax)\n             (recur is (cons (interval (inc imax) jmax) (next js))\n                    (conj r i))\n             :else\n             (recur (next is) (next js)\n                    (conj r (interval imin imax))))))))\n      (apply multi-interval r))))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "eq",
    "macro" true,
    "line" 1153,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro eq [& forms]\n  `(all\n    ~@(map eq-form forms)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["&" "forms"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "*",
    "line" 973,
    "column" 1,
    "doc"
    "A finite domain constraint for multiplication and\n   thus division. u, v & w must be eventually be given \n   domains if vars.",
    "tag" nil,
    "source"
    "(defn *\n  \"A finite domain constraint for multiplication and\n   thus division. u, v & w must be eventually be given \n   domains if vars.\"\n  [u v w]\n  (cgoal (*c u v w)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["u" "v" "w"]]}
   {"arglists" [["u" "v" "w"]],
    "ns" "clojure.core.logic.fd",
    "name" "-",
    "column" 1,
    "line" 903,
    "source" "(defn -\n  [u v w]\n  (+ v w u))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this" "that"]],
    "name" "-difference"}
   {"arglists" [["a" "x" "dom" "domp"]],
    "ns" "clojure.core.logic.fd",
    "name" "resolve-storable-dom",
    "column" 1,
    "line" 604,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "unify-with-domain*",
    "line" 34,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source"
    "(declare domain sorted-set->domain\n         difference* intersection* disjoint?*\n         unify-with-domain* finite-domain?\n         interval multi-interval)",
    "file" "clojure/core/logic/fd.clj",
    "arglists" nil}
   {"arglists" [["u" "v"]],
    "ns" "clojure.core.logic.fd",
    "name" "==c",
    "column" 1,
    "line" 718,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "IInterval",
    "line" 16,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IInterval\n  (-lb [this])\n  (-ub [this]))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" nil}
   {"arglists" [["x" "y*" "n*"]],
    "ns" "clojure.core.logic.fd",
    "name" "-distinct",
    "column" 1,
    "line" 1025,
    "source"
    "(defn -distinct [x y* n*]\n  (cgoal (-distinctc x y* n*)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["vars" "exprs"]],
    "ns" "clojure.core.logic.fd",
    "name" "->fd",
    "column" 1,
    "line" 1144,
    "source"
    "(defn ->fd [vars exprs]\n  `(fresh [~@vars]\n     ~@(reverse exprs)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "dom",
    "line" 628,
    "column" 1,
    "doc" "Assign a var x a domain.",
    "tag" nil,
    "source"
    "(defn dom\n  \"Assign a var x a domain.\"\n  [x dom]\n  (fn [a]\n    (let [domp (get-dom a x)\n          dom  (if domp\n                 (-intersection dom domp)\n                 dom)]\n      ((composeg\n         (process-dom x dom domp)\n         (if (and (nil? domp)\n                  (not (singleton-dom? dom)))\n           (domc x)\n           identity)) a))))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["x" "dom"]]}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this" "n"]],
    "name" "-keep-before"}
   {"arglists" [["i" "j"]],
    "ns" "clojure.core.logic.fd",
    "name" "interval->=",
    "column" 1,
    "line" 51,
    "source" "(defn interval->= [i j]\n  (core/>= (-lb i) (-ub j)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this"]],
    "name" "-ub"}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.fd",
    "name" "finite-domain?",
    "column" 1,
    "line" 145,
    "source" "(defn finite-domain? [x]\n  (instance? FiniteDomain x))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.fd",
    "name" "binops->fd",
    "column" 1,
    "line" 1096,
    "source"
    "(def binops->fd\n  '{+  clojure.core.logic.fd/+\n    -  clojure.core.logic.fd/-\n    *  clojure.core.logic.fd/*\n    /  clojure.core.logic.fd/quot\n    =  clojure.core.logic.fd/==\n    != clojure.core.logic.fd/!=\n    <= clojure.core.logic.fd/<=\n    <  clojure.core.logic.fd/<\n    >= clojure.core.logic.fd/>=\n    >  clojure.core.logic.fd/>})",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "extend-to-fd",
    "macro" true,
    "line" 168,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro extend-to-fd [t]\n  `(extend-type ~t\n     IMemberCount\n     (~'-member-count [this#] 1)\n\n     IInterval\n     (~'-lb [this#] this#)\n     (~'-ub [this#] this#)\n\n     ISortedDomain\n     (~'-drop-one [this#]\n       nil)\n     (~'-drop-before [this# n#]\n       (when (clojure.core/>= this# n#)\n         this#))\n     (~'-keep-before [this# n#]\n       (when (clojure.core/< this# n#)\n         this#))\n\n     ISet\n     (~'-member? [this# that#]\n       (if (integer? that#)\n         (= this# that#)\n         (-member? that# this#)))\n     (~'-disjoint? [this# that#]\n       (if (integer? that#)\n         (not= this# that#)\n         (-disjoint? that# this#)))\n     (~'-intersection [this# that#]\n       (cond\n        (integer? that#) (when (= this# that#)\n                           this#)\n        (interval? that#) (-intersection that# this#)\n        :else (intersection* this# that#)))\n     (~'-difference [this# that#]\n       (cond\n        (integer? that#) (if (= this# that#)\n                           nil\n                           this#)\n        (interval? that#) (-difference that# this#)\n        :else (difference* this# that#)))\n\n     IIntervals\n     (~'-intervals [this#]\n       (list this#))))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["t"]]}
   {"arglists" [["u" "v" "w"]],
    "ns" "clojure.core.logic.fd",
    "name" "*c",
    "column" 1,
    "line" 910,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "domain",
    "line" 155,
    "column" 1,
    "doc"
    "Construct a domain for assignment to a var. Arguments should \n   be integers given in sorted order. domains may be more efficient \n   than intervals when only a few values are possible.",
    "tag" nil,
    "source"
    "(defn domain\n  \"Construct a domain for assignment to a var. Arguments should \n   be integers given in sorted order. domains may be more efficient \n   than intervals when only a few values are possible.\"\n  [& args]\n  (when (seq args)\n    (sorted-set->domain (into (sorted-set) args))))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["&" "args"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "interval",
    "line" 356,
    "column" 1,
    "doc"
    "Construct an interval for an assignment to a var. intervals may\n   be more efficient that the domain type when the range of possiblities\n   is large.",
    "tag" nil,
    "source"
    "(defn interval\n  \"Construct an interval for an assignment to a var. intervals may\n   be more efficient that the domain type when the range of possiblities\n   is large.\"\n  ([ub] (IntervalFD. 0 ub))\n  ([lb ub]\n     (if (zero? (core/- ub lb))\n       ub\n       (IntervalFD. lb ub))))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["ub"] ["lb" "ub"]]}
   {"arglists" [["form"]],
    "ns" "clojure.core.logic.fd",
    "name" "eq-form",
    "column" 1,
    "line" 1148,
    "source"
    "(defn eq-form [form]\n  (let [vars (atom [])\n        exprs (eq* (expand form) vars)]\n    (->fd @vars exprs)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this" "that"]],
    "name" "-intersection"}
   {"ns" "clojure.core.logic.fd",
    "name" "bounded-listo",
    "line" 1082,
    "column" 1,
    "doc"
    "Ensure that the list l never grows beyond bound n.\n   n must have been assigned a domain.",
    "tag" nil,
    "source"
    "(defne bounded-listo\n  \"Ensure that the list l never grows beyond bound n.\n   n must have been assigned a domain.\"\n  [l n]\n  ([() _] (<= 0 n))\n  ([[h . t] n]\n     (fresh [m]\n       (in m (interval 0 Integer/MAX_VALUE))\n       (+ m 1 n)\n       (bounded-listo t m))))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.fd",
    "name" "domc",
    "column" 1,
    "line" 715,
    "source" "(defn domc [x]\n  (cgoal (-domc x)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["i" "j"]],
    "ns" "clojure.core.logic.fd",
    "name" "interval->",
    "column" 1,
    "line" 48,
    "source" "(defn interval-> [i j]\n  (core/> (-lb i) (-ub j)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["is" "js"]],
    "ns" "clojure.core.logic.fd",
    "name" "difference*",
    "column" 1,
    "line" 405,
    "source"
    "(defn difference* [is js]\n    (loop [is (seq (-intervals is)) js (seq (-intervals js)) r []]\n      (if is\n        (if js\n          (let [i (first is)\n                j (first js)]\n            (cond\n             (interval-< i j) (recur (next is) js (conj r i))\n             (interval-> i j) (recur is (next js) r)\n             :else\n             (let [[imin imax] (bounds i)\n                   [jmin jmax] (bounds j)]\n               (cond\n                (core/< imin jmin)\n                (cond\n                 (core/< jmax imax)\n                 (recur (cons (interval (inc jmax) imax) (next is))\n                        (next js)\n                        (conj r (interval imin (dec jmin))))\n                 (core/> jmax imax)\n                 (recur (next is)\n                        (cons (interval (inc imax) jmax) (next js))\n                        (conj r (interval imin (dec jmin))))\n                 :else\n                 (recur (next is) (next js)\n                        (conj r (interval imin (dec jmin)))))\n                (core/>= imin jmin)\n                (cond\n                 (core/< imax jmax)\n                 (recur (next is)\n                        (cons (interval (inc imax) jmax) (next js))\n                        r)\n                 (core/> imax jmax)\n                 (recur (cons (interval (inc jmax) imax) (next is))\n                        (next js)\n                        r)\n                 :else (recur (next is) (next js)\n                              r))))))\n          (apply multi-interval (into r is)))\n        (apply multi-interval r))))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["is"]],
    "ns" "clojure.core.logic.fd",
    "name" "normalize-intervals",
    "column" 1,
    "line" 558,
    "source"
    "(defn normalize-intervals [is]\n  (reduce (fn [r i]\n            (if (zero? (count r))\n              (conj r i)\n              (let [j (peek r)\n                    jmax (-ub j)\n                    imin (-lb i)]\n                (if (core/<= (dec imin) jmax)\n                  (conj (pop r) (interval (-lb j) (-ub i)))\n                  (conj r i)))))\n          [] is))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this"]],
    "name" "-lb"}
   {"arglists" [["i" "j"]],
    "ns" "clojure.core.logic.fd",
    "name" "interval-<",
    "column" 1,
    "line" 42,
    "source" "(defn interval-< [i j]\n  (core/< (-ub i) (-lb j)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.fd",
    "name" "-domc",
    "column" 1,
    "line" 689,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["u" "v" "w"]],
    "ns" "clojure.core.logic.fd",
    "name" "quot",
    "column" 1,
    "line" 980,
    "source" "(defn quot [u v w]\n  (* v w u))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["i" "j"]],
    "ns" "clojure.core.logic.fd",
    "name" "interval-<=",
    "column" 1,
    "line" 45,
    "source" "(defn interval-<= [i j]\n  (core/<= (-ub i) (-lb j)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this"]],
    "name" "-drop-one"}
   {"arglists" [["i"]],
    "ns" "clojure.core.logic.fd",
    "name" "bounds",
    "column" 1,
    "line" 39,
    "source" "(defn bounds [i]\n  (pair (-lb i) (-ub i)))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "<",
    "line" 822,
    "column" 1,
    "doc"
    "A finite domain constraint. u must be less than v. u and v\n   must eventually be given domains if vars.",
    "tag" nil,
    "source"
    "(defn <\n  \"A finite domain constraint. u must be less than v. u and v\n   must eventually be given domains if vars.\"\n  [u v]\n  (all\n   (<= u v)\n   (!= u v)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["u" "v"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "!=",
    "line" 783,
    "column" 1,
    "doc"
    "A finite domain constraint. u and v must not be equal. u and v\n   must eventually be given domains if vars.",
    "tag" nil,
    "source"
    "(defn !=\n  \"A finite domain constraint. u and v must not be equal. u and v\n   must eventually be given domains if vars.\"\n  [u v]\n  (cgoal (!=c u v)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["u" "v"]]}
   {"arglists" [["f"]],
    "ns" "clojure.core.logic.fd",
    "name" "map-sum",
    "column" 1,
    "line" 655,
    "source"
    "(defn map-sum [f]\n  (fn loop [ls]\n    (if (empty? ls)\n      (fn [a] nil)\n      (fn [a]\n        (mplus\n         ((f (first ls)) a)\n         (fn []\n           ((loop (rest ls)) a)))))))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "-distinctc",
    "line" 983,
    "column" 1,
    "doc"
    "The real *individual* distinct constraint. x is a var that now is bound to\n   a single value. y* were the non-singleton bound vars that existed at the\n   construction of the constraint. n* is the set of singleton domain values \n   that existed at the construction of the constraint. We use categorize to \n   determine the current non-singleton bound vars and singleton vlaues. if x\n   is in n* or the new singletons we have failed. If not we simply remove \n   the value of x from the remaining non-singleton domains bound to vars.",
    "tag" nil,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["x" "y*" "n*"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "distinctc",
    "line" 1039,
    "column" 1,
    "doc"
    "The real distinct constraint. v* can be seq of logic vars and\n   values or it can be a logic var itself. This constraint does not \n   run until v* has become ground. When it has become ground we group\n   v* into a set of logic vars and a sorted set of known singleton \n   values. We then construct the individual constraint for each var.",
    "tag" nil,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["v*"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "process-dom",
    "line" 613,
    "column" 1,
    "doc"
    "If x is a var we update its domain. If it's an integer\n   we check that it's a member of the given domain. dom is\n   then new domain, it should have already been calculated from\n   domp which was the previous domain.",
    "tag" nil,
    "source"
    "(defn process-dom\n  \"If x is a var we update its domain. If it's an integer\n   we check that it's a member of the given domain. dom is\n   then new domain, it should have already been calculated from\n   domp which was the previous domain.\"\n  [x dom domp]\n  (fn [a]\n    (when dom\n      (cond\n       (lvar? x) (resolve-storable-dom a x dom domp)\n       (-member? dom x) a\n       :else nil))))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["x" "dom" "domp"]]}
   {"ns" "clojure.core.logic.fd",
    "name" ">",
    "line" 830,
    "column" 1,
    "doc"
    "A finite domain constraint. u must be greater than v. u and v\n   must eventually be given domains if vars.",
    "tag" nil,
    "source"
    "(defn >\n  \"A finite domain constraint. u must be greater than v. u and v\n   must eventually be given domains if vars.\"\n  [u v]\n  (< v u))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["u" "v"]]}
   {"arglists" [[] ["i0"] ["i0" "i1"] ["i0" "i1" "&" "ir"]],
    "ns" "clojure.core.logic.fd",
    "name" "multi-interval",
    "column" 1,
    "line" 570,
    "source"
    "(defn multi-interval\n  ([] nil)\n  ([i0] i0)\n  ([i0 i1]\n     (let [is [i0 i1]]\n       (MultiIntervalFD. (reduce min (map -lb is)) (reduce max (map -ub is)) is)))\n  ([i0 i1 & ir]\n     (let [is (into [] (concat (list i0 i1) ir))]\n       (MultiIntervalFD. (reduce min (map -lb is)) (reduce max (map -ub is)) is))))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "ISet",
    "line" 28,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source"
    "(defprotocol ISet\n  (-member? [this n])\n  (-disjoint? [this that])\n  (-intersection [this that])\n  (-difference [this that]))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic.fd",
    "name" ">=",
    "line" 836,
    "column" 1,
    "doc"
    "A finite domain constraint. u must be greater than or equal to v.\n   u and v must eventually be given domains if vars.",
    "tag" nil,
    "source"
    "(defn >=\n  \"A finite domain constraint. u must be greater than or equal to v.\n   u and v must eventually be given domains if vars.\"\n  [u v]\n  (<= v u))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["u" "v"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic.fd",
    "name" "singleton-dom?",
    "column" 1,
    "line" 601,
    "source" "(defn singleton-dom? [x]\n  (integer? x))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "<=",
    "line" 816,
    "column" 1,
    "doc"
    "A finite domain constraint. u must be less than or equal to v.\n   u and v must eventually be given domains if vars.",
    "tag" nil,
    "source"
    "(defn <=\n  \"A finite domain constraint. u must be less than or equal to v.\n   u and v must eventually be given domains if vars.\"\n  [u v]\n  (cgoal (<=c u v)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["u" "v"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "->MultiIntervalFD",
    "line" 474,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.fd.MultiIntervalFD.",
    "tag" nil,
    "source"
    "(deftype MultiIntervalFD [min max is]\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :is is\n      :min min\n      :max max\n      not-found))\n\n  Object\n  (equals [this j]\n    (if (instance? MultiIntervalFD j)\n      (let [i this\n            [jmin jmax] (bounds j)]\n        (if (and (= min jmin) (= max jmax))\n          (let [is (normalize-intervals is)\n                js (normalize-intervals (-intervals j))]\n            (= is js))\n          false))\n      false))\n\n  IMemberCount\n  (-member-count [this]\n    ;; NOTE: ugly hack around http://dev.clojure.org/jira/browse/CLJ-1202 - David\n    (reduce core/+ 0 (map #(-member-count %) is)))\n\n  IInterval\n  (-lb [_] min)\n  (-ub [_] max)\n\n  ISortedDomain\n  (-drop-one [_]\n    (let [i (first is)]\n      (if (singleton-dom? i)\n        (let [nis (rest is)]\n          (MultiIntervalFD. (-lb (first nis)) max nis))\n        (let [ni (-drop-one i)]\n          (MultiIntervalFD. (-lb ni) max (cons ni (rest is)))))))\n\n  (-drop-before [_ n]\n    (let [is (seq is)]\n      (loop [is is r []]\n        (if is\n          (let [i (-drop-before (first is) n)]\n            (if i\n              (recur (next is) (conj r i))\n              (recur (next is) r)))\n          (when (pos? (count r))\n            (apply multi-interval r))))))\n\n  (-keep-before [_ n]\n    (let [is (seq is)]\n      (loop [is is r []]\n        (if is\n          (let [i (-keep-before (first is) n)]\n            (if i\n              (recur (next is) (conj r i))\n              (recur (next is) r)))\n          (when (pos? (count r))\n            (apply multi-interval r))))))\n\n  ISet\n  (-member? [this n]\n    (if (some #(-member? % n) is)\n      true\n      false))\n  (-disjoint? [this that]\n    (disjoint?* this that))\n  (-intersection [this that]\n    (intersection* this that))\n  (-difference [this that]\n    (difference* this that))\n\n  IIntervals\n  (-intervals [this]\n    (seq is))\n\n  IMergeDomains\n  (-merge-doms [this that]\n    (-intersection this that)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["min" "max" "is"]]}
   {"ns" "clojure.core.logic.fd",
    "name" "->IntervalFD",
    "line" 230,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.fd.IntervalFD.",
    "tag" nil,
    "source"
    "(deftype IntervalFD [lb ub]\n  Object\n  (equals [_ o]\n    (if (instance? IntervalFD o)\n      (and (= lb (-lb o))\n           (= ub (-ub o)))\n      false))\n\n  (toString [this]\n    (pr-str this))\n\n  IMemberCount\n  (-member-count [this] (inc (core/- ub lb)))\n\n  IInterval\n  (-lb [_] lb)\n  (-ub [_] ub)\n\n  ISortedDomain\n  (-drop-one [_]\n    (let [nlb (inc lb)]\n      (when (core/<= nlb ub)\n        (interval nlb ub))))\n\n  (-drop-before [this n]\n    (cond\n     (= n ub) n\n     (core/< n lb) this\n     (core/> n ub) nil\n     :else (interval n ub)))\n\n  (-keep-before [this n]\n    (cond\n     (core/<= n lb) nil\n     (core/> n ub) this\n     :else (interval lb (dec n))))\n\n  ISet\n  (-member? [this n]\n    (and (core/>= n lb) (core/<= n ub)))\n\n  (-disjoint? [this that]\n    (cond\n     (integer? that)\n     (not (-member? this that))\n\n     (interval? that)\n     (let [i this\n           j that\n           [imin imax] (bounds i)\n           [jmin jmax] (bounds j)]\n       (or (core/> imin jmax)\n           (core/< imax jmin)))\n\n     :else (disjoint?* this that)))\n\n  (-intersection [this that]\n    (cond\n     (integer? that)\n     (if (-member? this that)\n       that\n       nil)\n\n     (interval? that)\n     (let [i this j that\n           imin (-lb i) imax (-ub i)\n           jmin (-lb j) jmax (-ub j)]\n       (cond\n        (core/< imax jmin) nil\n        (core/< jmax imin) nil\n        (and (core/<= imin jmin)\n             (core/>= imax jmax)) j\n        (and (core/<= jmin imin)\n             (core/>= jmax imax)) i\n        (and (core/<= imin jmin)\n             (core/<= imax jmax)) (interval jmin imax)\n        (and (core/<= jmin imin)\n             (core/<= jmax imax)) (interval imin jmax)\n        :else (throw (Error. (str \"Interval intersection not defined \" i \" \" j)))))\n\n     :else (intersection* this that)))\n\n  (-difference [this that]\n    (cond\n     (integer? that)\n     (cond\n      (= lb that) (interval (inc lb) ub)\n      (= ub that) (interval lb (dec ub))\n      :else (if (-member? this that)\n              (multi-interval (interval lb (dec that))\n                              (interval (inc that) ub))\n              this))\n     \n     (interval? that)\n     (let [i this j that\n           imin (-lb i) imax (-ub i)\n           jmin (-lb j) jmax (-ub j)]\n       (cond\n        (core/> jmin imax) i\n        (and (core/<= jmin imin)\n             (core/>= jmax imax)) nil\n        (and (core/< imin jmin)\n             (core/> imax jmax)) (multi-interval (interval imin (dec jmin))\n             (interval (inc jmax) imax))\n        (and (core/< imin jmin)\n             (core/<= jmin imax)) (interval imin (dec jmin))\n        (and (core/> imax jmax)\n             (core/<= jmin imin)) (interval (inc jmax) imax)\n        :else (throw (Error. (str \"Interval difference not defined \" i \" \" j)))))\n     \n     :else (difference* this that)))\n\n  IIntervals\n  (-intervals [this]\n    (list this))\n\n  IMergeDomains\n  (-merge-doms [this that]\n    (-intersection this that)))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" [["lb" "ub"]]}
   {"arglists" [["u" "v"]],
    "ns" "clojure.core.logic.fd",
    "name" "<=c",
    "column" 1,
    "line" 789,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["a" "x" "dom" "domp"]],
    "ns" "clojure.core.logic.fd",
    "name" "ext-dom-fd",
    "column" 1,
    "line" 594,
    "source" nil,
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["is" "js"]],
    "ns" "clojure.core.logic.fd",
    "name" "disjoint?*",
    "column" 1,
    "line" 446,
    "source"
    "(defn disjoint?* [is js]\n  (if (-disjoint? (interval (-lb is) (-ub is))\n                 (interval (-lb js) (-ub js)))\n    true\n    (let [d0 (-intervals is)\n          d1 (-intervals js)]\n      (loop [d0 d0 d1 d1]\n        (if (or (nil? d0) (nil? d1))\n          true\n          (let [i (first d0)\n                j (first d1)]\n            (cond\n              (interval-< i j) (recur (next d0) d1)\n              (interval-> i j) (recur d0 (next d1))\n              (-disjoint? i j)  (recur (next d0) d1)\n              :else false)))))))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"arglists" [["s"]],
    "ns" "clojure.core.logic.fd",
    "name" "sorted-set->domain",
    "column" 1,
    "line" 148,
    "source"
    "(defn sorted-set->domain [s]\n  (let [c (count s)]\n    (cond\n     (zero? c) nil\n     (= c 1) (first s)\n     :else (FiniteDomain. s (first s) (first (rseq s))))))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"source" nil,
    "tag" nil,
    "ns" "clojure.core.logic.fd",
    "doc" nil,
    "arglists" [["this" "n"]],
    "name" "-drop-before"}
   {"arglists" [["dom"]],
    "ns" "clojure.core.logic.fd",
    "name" "to-vals",
    "column" 1,
    "line" 665,
    "source"
    "(defn to-vals [dom]\n  (letfn [(to-vals* [is]\n            (when is\n              (let [i (first is)]\n                (lazy-seq\n                 (cons (-lb i)\n                       (if-let [ni (-drop-one i)]\n                         (to-vals* (cons ni (next is)))\n                         (to-vals* (next is))))))))]\n    (to-vals* (seq (-intervals dom)))))",
    "file" "clojure/core/logic/fd.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.fd",
    "name" "IIntervals",
    "line" 20,
    "column" 1,
    "doc" nil,
    "tag" nil,
    "source" "(defprotocol IIntervals\n  (-intervals [this]))",
    "file" "clojure/core/logic/fd.clj",
    "arglists" nil}],
  "clojure.core.logic.dcg"
  [{"arglists" [["clauses"]],
    "ns" "clojure.core.logic.dcg",
    "name" "count-clauses",
    "column" 1,
    "line" 37,
    "source"
    "(defn count-clauses [clauses]\n  (if (fresh-expr? clauses)\n    (count-clauses (drop 2 clauses))\n    (reduce (fn [s c]\n              (cond\n               (fresh-expr? c) (+ s (count-clauses (drop 2 c)))\n               (!dcg? c) s\n               :else (clojure.core/inc s)))\n            0 clauses)))",
    "file" "clojure/core/logic/dcg.clj",
    "tag" nil}
   {"arglists" [["clause"]],
    "ns" "clojure.core.logic.dcg",
    "name" "!dcg?",
    "column" 1,
    "line" 14,
    "source"
    "(defn !dcg? [clause]\n  (and (sequential? clause)\n       (let [f (first clause)]\n         (and (symbol? f)\n              (= (name f) \"!dcg\")))))",
    "file" "clojure/core/logic/dcg.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.dcg",
    "name" "def-->",
    "macro" true,
    "line" 92,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro def--> [name args & clauses]\n  (let [r (range 1 (+ (count-clauses clauses) 2))\n        lsyms (map lsym r)\n        clauses (mark-clauses clauses)\n        clauses (handle-clauses lsyms clauses)]\n   `(defn ~name [~@args ~(first lsyms) ~(last lsyms)]\n      (fresh [~@(butlast (rest lsyms))]\n        ~@clauses))))",
    "file" "clojure/core/logic/dcg.clj",
    "arglists" [["name" "args" "&" "clauses"]]}
   {"arglists"
    [["env" ["m" "as" "c"] "i"] ["env" ["m" "as" "c"] "i" "quoted"]],
    "ns" "clojure.core.logic.dcg",
    "name" "->lcons",
    "column" 1,
    "line" 20,
    "source"
    "(defn ->lcons\n  ([env [m :as c] i] (->lcons env c i false))\n  ([env [m :as c] i quoted]\n     (cond\n      (empty? c) `(fresh []\n                    (== ~(env (dec i)) ~(env i)))\n      :else (let [m (if quoted `(quote ~m) m)]\n              `(== ~(env (dec i)) (lcons ~m ~(env i)))))))",
    "file" "clojure/core/logic/dcg.clj",
    "tag" nil}
   {"arglists" [["clause"]],
    "ns" "clojure.core.logic.dcg",
    "name" "fresh-expr?",
    "column" 1,
    "line" 29,
    "source"
    "(defn fresh-expr? [clause]\n  (and (seq? clause)\n       (let [f (first clause)]\n         (and (symbol? f)\n              (= (name f) \"fresh\")))))",
    "file" "clojure/core/logic/dcg.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.dcg",
    "name" "def-->e",
    "macro" true,
    "line" 117,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro def-->e [name args & pcss]\n  (let [fsym (gensym \"l1_\")\n        osym (gensym \"o\")]\n   `(defne ~name [~@args ~fsym ~osym]\n      ~@(map (fn [[p & cs]]\n               (list (-> p (conj '_) (conj '_))\n                     (handle-cclause fsym osym cs)))\n             pcss))))",
    "file" "clojure/core/logic/dcg.clj",
    "arglists" [["name" "args" "&" "pcss"]]}
   {"arglists" [["n"]],
    "ns" "clojure.core.logic.dcg",
    "name" "lsym",
    "column" 1,
    "line" 11,
    "source" "(defn lsym [n]\n  (gensym (str \"l\" n \"_\")))",
    "file" "clojure/core/logic/dcg.clj",
    "tag" nil}
   {"arglists" [["cs"] [["c" "&" "r" "as" "cs"] "i"]],
    "ns" "clojure.core.logic.dcg",
    "name" "mark-clauses",
    "column" 1,
    "line" 49,
    "source"
    "(defn mark-clauses\n  ([cs] (mark-clauses cs (atom 0)))\n  ([[c & r :as cs] i]\n     (cond\n      (nil? (seq cs)) ()\n      (fresh-expr? c) (cons `(fresh ~(second c)\n                          ~@(mark-clauses (drop 2 c) i))\n                       (mark-clauses r i))\n      (!dcg? c) (cons c (mark-clauses r i))\n      :else (cons (with-meta c\n                    {:index (swap! i clojure.core/inc)})\n                  (mark-clauses r i)))))",
    "file" "clojure/core/logic/dcg.clj",
    "tag" nil}
   {"arglists" [["env" ["c" "&" "r" "as" "cs"]]],
    "ns" "clojure.core.logic.dcg",
    "name" "handle-clauses",
    "column" 1,
    "line" 65,
    "source"
    "(defn handle-clauses [env [c & r :as cs]]\n  (cond\n   (nil? (seq cs)) ()\n   (fresh-expr? c) (cons `(fresh ~(second c)\n                       ~@(handle-clauses env (drop 2 c)))\n                    (handle-clauses env r))\n   (!dcg? c) (cons (second c) (handle-clauses env r))\n   (vector? c) (cons (->lcons env c (-> c meta :index))\n                     (handle-clauses env r))\n   (and (seq? c)\n        (= (first c) `quote)\n        (vector? (second c))) (cons (->lcons env (second c) (-> c meta :index) true)\n                                     (handle-clauses env r))\n   :else (let [i (-> c meta :index)\n               c (if (seq? c) c (list c))]\n           (cons (concat c [(env (dec i)) (env i)])\n                 (handle-clauses env r)))))",
    "file" "clojure/core/logic/dcg.clj",
    "tag" nil}
   {"arglists" [["fsym" "osym" "cclause"]],
    "ns" "clojure.core.logic.dcg",
    "name" "handle-cclause",
    "column" 1,
    "line" 101,
    "source"
    "(defn handle-cclause [fsym osym cclause]\n  (let [c (count-clauses cclause)\n        r (range 2 (clojure.core/inc c))\n        lsyms (conj (into [fsym] (map lsym r)) osym)\n        clauses (mark-clauses cclause)\n        clauses (handle-clauses lsyms clauses)]\n    `(fresh [~@(butlast (rest lsyms))]\n       ~@clauses)))",
    "file" "clojure/core/logic/dcg.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.dcg",
    "name" "-->e",
    "macro" true,
    "line" 110,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro -->e [name & cclauses]\n  (let [fsym (gensym \"l1_\")\n        osym (gensym \"o\")]\n   `(defn ~name [~fsym ~osym]\n      (conde\n       ~@(map list (map (partial handle-cclause fsym osym) cclauses))))))",
    "file" "clojure/core/logic/dcg.clj",
    "arglists" [["name" "&" "cclauses"]]}
   {"ns" "clojure.core.logic.dcg",
    "name" "-->",
    "macro" true,
    "line" 83,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro --> [name & clauses]\n  (let [r (range 1 (+ (count-clauses clauses) 2))\n        lsyms (into [] (map lsym r))\n        clauses (mark-clauses clauses)\n        clauses (handle-clauses lsyms clauses)]\n    `(defn ~name [~(first lsyms) ~(last lsyms)]\n       (fresh [~@(butlast (rest lsyms))]\n         ~@clauses))))",
    "file" "clojure/core/logic/dcg.clj",
    "arglists" [["name" "&" "clauses"]]}],
  "clojure.core.logic"
  [{"arglists" [["k" "v"]],
    "ns" "clojure.core.logic",
    "name" "annotate",
    "column" 1,
    "line" 532,
    "source"
    "(defn annotate [k v]\n  (fn [a]\n    (vary-meta a assoc k v)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "->sym",
    "line" 1762,
    "column" 1,
    "tag" nil,
    "source" "(defn- ->sym [& args]\n  (symbol (apply str args)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "args"]]}
   {"ns" "clojure.core.logic",
    "name" "pred",
    "macro" true,
    "line" 1277,
    "column" 1,
    "doc"
    "Check a predicate against the value logic var. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro pred\n  \"Check a predicate against the value logic var. Non-relational.\"\n  [v f]\n  `(project [~v]\n     (== (~f ~v) true)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["v" "f"]]}
   {"arglists" [["a" "f"]],
    "ns" "clojure.core.logic",
    "name" "choice",
    "column" 1,
    "line" 1078,
    "source" "(defn choice [a f]\n  (Choice. a f))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic",
    "name" "cgoal",
    "column" 1,
    "line" 2339,
    "source"
    "(defn cgoal [c]\n  (reify\n    clojure.lang.IFn\n    (invoke [_ a]\n      (let [c' (-step c a)]\n        (if (-runnable? c')\n          (when-let [a (c' a)]\n            (let [c' (-step c a)]\n              (if (and (ientailed? c')\n                       (not (entailed? c c' a)))\n                ((addcg c) a)\n                a)))\n          ((addcg c) a))))\n    IUnwrapConstraint\n    (-unwrap [_] c)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "enforce-constraints",
    "column" 1,
    "line" 2308,
    "source"
    "(defn enforce-constraints [x]\n  (all\n   (force-ans x)\n   (fn [a]\n     (let [constrained (enforceable-constrained a)]\n       (verify-all-bound a constrained)\n       ((onceo (force-ans constrained)) a)))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "all",
    "macro" true,
    "line" 1217,
    "column" 1,
    "doc" "Like fresh but does does not create logic variables.",
    "tag" nil,
    "source"
    "(defmacro all\n  \"Like fresh but does does not create logic variables.\"\n  ([] `clojure.core.logic/s#)\n  ([& goals] `(fn [a#] (bind* a# ~@goals))))",
    "file" "clojure/core/logic.clj",
    "arglists" [[] ["&" "goals"]]}
   {"arglists" [["ifn" "arglist"]],
    "ns" "clojure.core.logic",
    "name" "apply-to-helper",
    "column" 1,
    "line" 1799,
    "source" "(def-apply-to-helper 20)",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "x" "dom"]],
    "ns" "clojure.core.logic",
    "name" "get-dom",
    "column" 1,
    "line" 500,
    "source"
    "(defn get-dom [s x dom]\n  (let [v (root-val s x)]\n    (cond\n      (subst-val? v) (let [v' (:v v)]\n                       (if (not= v' ::unbound)\n                         v'\n                         (-> v :doms dom)))\n      (not (lvar? v)) v)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "u" "v"]],
    "ns" "clojure.core.logic",
    "name" "ext",
    "column" 1,
    "line" 205,
    "source"
    "(defn ext [s u v]\n  (if (and (:oc s) (occurs-check s u (if (subst-val? v) (:v v) v)))\n    nil\n    (ext-no-check s u v)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "index-sym",
    "line" 1842,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- index-sym [name arity o]\n  (->sym name \"_\" arity \"-\" o \"-index\"))",
    "file" "clojure/core/logic.clj",
    "arglists" [["name" "arity" "o"]]}
   {"ns" "clojure.core.logic",
    "name" "normalize-store",
    "line" 2526,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source" "(declare normalize-store ground-term?)",
    "file" "clojure/core/logic.clj",
    "arglists" nil}
   {"arglists" [["v"]],
    "ns" "clojure.core.logic",
    "name" "to-s",
    "column" 1,
    "line" 528,
    "source"
    "(defn to-s [v]\n  (let [s (reduce (fn [m [k v]] (assoc m k v)) {} v)]\n    (make-s s (make-cs))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "==",
    "line" 1132,
    "column" 1,
    "doc" "A goal that attempts to unify terms u and v.",
    "tag" nil,
    "source"
    "(defn ==\n  \"A goal that attempts to unify terms u and v.\"\n  [u v]\n  (fn [a]\n    (let [has-cs? (pos? (count (:cs a)))]\n      (let [ap (unify (if has-cs? (assoc a :vs []) a) u v)\n            vs (if has-cs? (:vs ap))\n            changed? (pos? (count vs))]\n        (if changed?\n          ((run-constraints* vs (:cs ap) ::subst) (assoc ap :vs nil))\n          ap)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["u" "v"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "suspended-stream?",
    "column" 1,
    "line" 2011,
    "source"
    "(defn suspended-stream? [x]\n  (instance? SuspendedStream x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "->SuspendedStream",
    "line" 2003,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.SuspendedStream.",
    "tag" nil,
    "source"
    "(defrecord SuspendedStream [cache ansv* f]\n  ISuspendedStream\n  (ready? [this]\n    (not (identical? (:ansl @cache) ansv*))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["cache" "ansv*" "f"]]}
   {"arglists" [["u" "v" "s"]],
    "ns" "clojure.core.logic",
    "name" "unify-with-sequential*",
    "column" 1,
    "line" 869,
    "source"
    "(defn unify-with-sequential* [u v s]\n  (cond\n    (sequential? v)\n    (if (and (counted? u) (counted? v)\n          (not= (count u) (count v)))\n      nil\n      (loop [u u v v s s]\n        (if (seq u)\n          (if (seq v)\n            (if-let [s (unify s (first u) (first v))]\n              (recur (next u) (next v) s)\n              nil)\n            nil)\n          (if (seq v) nil s))))\n    \n    (lcons? v) (unify-terms v u s)\n    :else nil))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["v"]],
    "ns" "clojure.core.logic",
    "name" "to-subst-val",
    "column" 1,
    "line" 589,
    "source"
    "(defn to-subst-val [v]\n  (if (subst-val? v)\n    v\n    (subst-val ::unbound)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["xcs"]],
    "ns" "clojure.core.logic",
    "name" "run-constraints",
    "column" 1,
    "line" 2257,
    "source"
    "(defn run-constraints [xcs]\n  (fn [a]\n    (let [cq (:cq a)\n          a  (reduce (fn [a c]\n                       (queue a c))\n               (assoc a :cq (or cq [])) xcs)]\n     (if cq\n       a\n       (fix-constraints a)))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic",
    "name" "u#",
    "column" 1,
    "line" 1126,
    "source" "(def u# fail)",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["a"]],
    "ns" "clojure.core.logic",
    "name" "sort-by-member-count",
    "column" 1,
    "line" 2370,
    "source"
    "(defn sort-by-member-count [a]\n  (fn [x y]\n    (let-dom a [x dx y dy]\n      (< (-member-count dx) (-member-count dy)))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "v"]],
    "ns" "clojure.core.logic",
    "name" "-reify*",
    "column" 1,
    "line" 244,
    "source"
    "(defn -reify* [s v]\n  (let [v (walk s v)]\n    (reify-term v s)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "defrel",
    "macro" true,
    "line" 1840,
    "column" 1,
    "doc"
    "Define a relation for adding facts. Takes a name and some fields.\n         Use fact/facts to add facts and invoke the relation to query it.",
    "tag" nil,
    "source" "(RelHelper 20)",
    "file" "clojure/core/logic.clj",
    "arglists" [["name" "&" "rest"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "sym-helper",
    "line" 1756,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- sym-helper [prefix n]\n  (symbol (str prefix n)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["prefix" "n"]]}
   {"arglists" [["s" "u" "v"] ["s" "u" "v" "cs"]],
    "ns" "clojure.core.logic",
    "name" "disunify",
    "column" 1,
    "line" 2442,
    "source"
    "(defn disunify\n  ([s u v] (disunify s u v {:prefixc {}}))\n  ([s u v cs]\n     (if (identical? u v)\n       cs\n       (let [u (walk s u)\n             v (walk s v)]\n         (if (identical? u v)\n           cs\n           (if (and (not (lvar? u)) (lvar? v))\n             (-disunify-terms v u s cs)\n             (-disunify-terms u v s cs)))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "x" "y"]],
    "ns" "clojure.core.logic",
    "name" "entangle",
    "column" 1,
    "line" 594,
    "source"
    "(defn entangle [s x y]\n  (let [x  (root-var s x)\n        y  (root-var s y)\n        xv (to-subst-val (root-val s x))\n        yv (to-subst-val (root-val s y))]\n    (-> s\n      (update-var x (assoc xv :eset (conj (or (:eset xv) #{}) y)))\n      (update-var y (assoc yv :eset (conj (or (:eset yv) #{}) x))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["a" "c"]],
    "ns" "clojure.core.logic",
    "name" "var-rands",
    "column" 1,
    "line" 84,
    "source"
    "(defn var-rands [a c]\n  (->> (-rands c)\n    (map #(root-var a %))\n    (filter lvar?)\n    (into [])))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists"
    [["x"]
     ["x" "doms"]
     ["x" "doms" "_meta"]
     ["x" "doms" "eset" "_meta"]],
    "ns" "clojure.core.logic",
    "name" "subst-val",
    "column" 1,
    "line" 190,
    "source"
    "(defn subst-val\n  ([x] (SubstValue. x nil nil))\n  ([x doms] (SubstValue. x doms nil))\n  ([x doms _meta] (with-meta (SubstValue. x doms nil) _meta))\n  ([x doms eset _meta] (with-meta (SubstValue. x doms eset) _meta)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["&" "syms"]],
    "ns" "clojure.core.logic",
    "name" "env-locals",
    "column" 1,
    "line" 1566,
    "source"
    "(defn env-locals [& syms]\n  (disj (set (apply concat syms)) '_))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "v" "seenset" "f"]],
    "ns" "clojure.core.logic",
    "name" "sync-eset",
    "column" 1,
    "line" 440,
    "source"
    "(defn sync-eset [s v seenset f]\n  (if (not= seenset ::no-prop)\n    (reduce\n      (fn [s y]\n        (let [y (root-var s y)]\n          (if-not (contains? seenset y)\n            (f s y)\n            s)))\n      s\n      (:eset v))\n    s))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["p"]],
    "ns" "clojure.core.logic",
    "name" "recover-vars",
    "column" 1,
    "line" 2517,
    "source"
    "(defn recover-vars [p]\n  (loop [p (seq p) r #{}]\n    (if p\n      (let [[u v] (first p)]\n        (recur (next p)\n          (clojure.set/union\n            r (recover-vars-from-term u) (recover-vars-from-term v))))\n      r)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "name-with-attributes",
    "line" 1539,
    "column" 1,
    "doc"
    "To be used in macro definitions.\n   Handles optional docstrings and attribute maps for a name to be defined\n   in a list of macro arguments. If the first macro argument is a string\n   it is added as a docstring to name and removed from the macro argument\n   list. If afterwards the first macro argument is a map, its entries are\n   added to the name's metadata map and the map is removed from the\n   macro argument list. The return value is a vector containing the name\n   with its extended metadata map and the list of unprocessed macro\n   arguments.",
    "tag" nil,
    "source"
    "(defn- name-with-attributes\n  \"To be used in macro definitions.\n   Handles optional docstrings and attribute maps for a name to be defined\n   in a list of macro arguments. If the first macro argument is a string\n   it is added as a docstring to name and removed from the macro argument\n   list. If afterwards the first macro argument is a map, its entries are\n   added to the name's metadata map and the map is removed from the\n   macro argument list. The return value is a vector containing the name\n   with its extended metadata map and the list of unprocessed macro\n   arguments.\"\n  [name macro-args]\n  (let [[docstring macro-args] (if (string? (first macro-args))\n                                 [(first macro-args) (next macro-args)]\n                                 [nil macro-args])\n    [attr macro-args]          (if (map? (first macro-args))\n                                 [(first macro-args) (next macro-args)]\n                                 [{} macro-args])\n    attr                       (if docstring\n                                 (assoc attr :doc docstring)\n                                 attr)\n    attr                       (if (meta name)\n                                 (conj (meta name) attr)\n                                 attr)]\n    [(with-meta name attr) macro-args]))",
    "file" "clojure/core/logic.clj",
    "arglists" [["name" "macro-args"]]}
   {"ns" "clojure.core.logic",
    "name" "defne",
    "macro" true,
    "line" 1640,
    "column" 1,
    "doc"
    "Define a goal fn. Supports pattern matching. All\n   patterns will be tried. See conde.",
    "tag" nil,
    "source"
    "(defmacro defne\n  \"Define a goal fn. Supports pattern matching. All\n   patterns will be tried. See conde.\"\n  [& rest]\n  `(defnm conde ~@rest))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "rest"]]}
   {"arglists" [["x" "f" "reifier"] ["x" "f" "runnable" "reifier"]],
    "ns" "clojure.core.logic",
    "name" "-fixc",
    "column" 1,
    "line" 2877,
    "source"
    "(defn -fixc\n  ([x f reifier] (-fixc x f nil reifier))\n  ([x f runnable reifier]\n     (reify\n       IConstraintStep\n       (-step [this s]\n         (let [xv (walk s x)]\n           (reify\n             clojure.lang.IFn\n             (invoke [_ s]\n               ((composeg (f xv s reifier) (remcg this)) s))\n             IRunnable\n             (-runnable? [_]\n               (if (fn? runnable)\n                 (runnable x s)\n                 (not (lvar? xv)))))))\n       IConstraintOp\n       (-rator [_] `fixc)\n       (-rands [_] (if (vector? x) x [x]))\n       IReifiableConstraint\n       (-reifyc [c v r s]\n         (if (fn? reifier)\n           (reifier c x v r s)\n           (let [x (walk* r x)]\n             `(fixc ~x ~reifier))))\n       IConstraintWatchedStores\n       (-watched-stores [this] #{::subst}))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["a" "c"]],
    "ns" "clojure.core.logic",
    "name" "unbound-rands",
    "column" 1,
    "line" 90,
    "source"
    "(defn unbound-rands [a c]\n  (->> (var-rands a c)\n    (filter #(lvar? (root-val a %)))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "fnc",
    "macro" true,
    "line" 2724,
    "column" 1,
    "doc"
    "Define an anonymous constraint that can be used with the unifier:\n\n     (let [oddc (fnc [x] (odd? x))]\n\n       (unifier {:a '?a} {:a 1} :when {'?a oddc})\n         ;;=> {:a 1}\n\n       (unifier {:a '?a} {:a 2} :when {'?a oddc})\n         ;;=> nil\n     )\n\n  Note, the constraint will not run until all arguments are fully ground.\n\n  Use defnc to define a constraint and assign a toplevel var.",
    "tag" nil,
    "source"
    "(defmacro fnc\n  \"Define an anonymous constraint that can be used with the unifier:\n\n     (let [oddc (fnc [x] (odd? x))]\n\n       (unifier {:a '?a} {:a 1} :when {'?a oddc})\n         ;;=> {:a 1}\n\n       (unifier {:a '?a} {:a 2} :when {'?a oddc})\n         ;;=> nil\n     )\n\n  Note, the constraint will not run until all arguments are fully ground.\n\n  Use defnc to define a constraint and assign a toplevel var.\"\n  [args & body]\n  (let [name (symbol (gensym \"fnc\"))]\n    `(fn ~args\n       (letfn [(~name [~@args]\n                 (reify\n                   clojure.core.logic.protocols/IConstraintStep\n                   (-step [this# a#]\n                     (reify\n                       ~'clojure.lang.IFn\n                       (~'invoke [_# a#]\n                         (let [[~@args :as args#] (map #(clojure.core.logic/walk* a# %) ~args)\n                                test# (do ~@body)]\n                           (when test#\n                             ((clojure.core.logic/remcg this#) a#))))\n                       clojure.core.logic.protocols/IRunnable\n                       (~'-runnable? [_#]\n                         (clojure.core.logic/ground-term? ~args a#))))\n                   clojure.core.logic.protocols/IConstraintOp\n                   (~'-rator [_#] '~name)\n                   (~'-rands [_#] (filter clojure.core.logic/lvar? (flatten ~args)))\n                   clojure.core.logic.protocols/IReifiableConstraint\n                   (~'-reifyc [_# _# r# a#]\n                     (list '~name (map #(clojure.core.logic/-reify r# %) ~args)))\n                   clojure.core.logic.protocols/IConstraintWatchedStores\n                   (~'-watched-stores [_#] #{:clojure.core.logic/subst})))]\n         (cgoal (~name ~@args))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["args" "&" "body"]]}
   {"ns" "clojure.core.logic",
    "name" "resto",
    "line" 1612,
    "column" 1,
    "doc"
    "A relation where l is a collection, such that d is the rest of l",
    "tag" nil,
    "source"
    "(defn resto\n  \"A relation where l is a collection, such that d is the rest of l\"\n  [l d]\n  (fresh [a]\n    (== (lcons a d) l)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["l" "d"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "defrel-helper",
    "line" 1765,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- defrel-helper [name arity args]\n  (let [r (range 1 (+ arity 2))\n        arity-excs (fn [n] `(arity-exc-helper '~name ~n))]\n    (if (seq args)\n      `(do\n         (def ~name\n           (.withMeta\n            (~'clojure.core.logic.Rel.\n             '~name (atom {}) nil ~@(map arity-excs r))\n            {:ns ~'*ns*}))\n         (extend-rel ~name ~@args))\n      `(def ~name\n         (.withMeta\n          (~'clojure.core.logic.Rel. '~name (atom {}) nil ~@(map arity-excs r))\n          {:ns ~'*ns*})))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["name" "arity" "args"]]}
   {"arglists" [["s" "g"] ["s" "q" "g"]],
    "ns" "clojure.core.logic",
    "name" "solutions",
    "column" 1,
    "line" 1222,
    "source"
    "(defn solutions\n  ([s g]\n     (solutions s (lvar) g))\n  ([s q g]\n     (take* ((all g (reifyg q)) s))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["p"]],
    "ns" "clojure.core.logic",
    "name" "!=c",
    "column" 1,
    "line" 2528,
    "source"
    "(defn !=c [p]\n  (reify\n    ITreeConstraint\n    IConstraintStep\n    (-step [this s]\n      (reify\n        clojure.lang.IFn\n        (invoke [_ s]\n          (let [p (loop [sp (seq p) p p]\n                    (if sp\n                      (let [[x v] (first sp)\n                             ;; TODO: this seems expensive to walk* both sides\n                             ;; and run an equality test there must be a better\n                             ;; way - David\n                             xv (walk* s x)\n                             vv (walk* s v)]\n                        (cond\n                          (= xv vv) (recur (next sp) (dissoc p x))\n                          (nil? (unify s xv vv)) nil\n                          :else (recur (next sp) (assoc (dissoc p x) xv vv))))\n                      p))]\n            (if p\n              (when-not (empty? p)\n                ((composeg*\n                   (remcg this)\n                   (cgoal (!=c p))) s))\n              ((remcg this) s))))\n        IRunnable\n        (-runnable? [_]\n          (some #(not= (walk s %) %) (recover-vars p)))\n        IEntailed\n        (-entailed? [_]\n          (empty? p))))\n    IPrefix\n    (-prefix [_] p)\n    IWithPrefix\n    (-with-prefix [_ p] (!=c p))\n    IReifiableConstraint\n    (-reifyc [this v r a]\n      (let [p* (-reify a (map (fn [[lhs rhs]] `(~lhs ~rhs)) p) r)]\n        (if (empty? p*)\n          '()\n          `(~'!= ~@p*))))\n    IConstraintOp\n    (-rator [_] `!=)\n    (-rands [_] (seq (recover-vars p)))\n    IConstraintWatchedStores\n    (-watched-stores [this] #{::subst})))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "fix-constraints",
    "line" 2240,
    "column" 1,
    "doc"
    "A goal to run the constraints in cq until it is empty. Of\n   course running a constraint may grow cq so this function\n   finds the fixpoint.",
    "tag" nil,
    "source"
    "(defn fix-constraints\n  \"A goal to run the constraints in cq until it is empty. Of\n   course running a constraint may grow cq so this function\n   finds the fixpoint.\"\n  [a]\n  (loop [a a]\n    (when a\n      (let [cq (:cq a)]\n        (if (zero? (count cq))\n          (assoc a :cq nil)\n          (let [c (first cq)]\n            (recur\n              ((run-constraint c)\n               (-> a\n                 (assoc :cq (subvec (:cq a) 1))\n                 (assoc :cqs (disj (:cqs a) (id c))))))))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a"]]}
   {"arglists" [["a" "constrained"]],
    "ns" "clojure.core.logic",
    "name" "verify-all-bound",
    "column" 1,
    "line" 2281,
    "source"
    "(defn verify-all-bound [a constrained]\n  (letfn [(verify-all-bound* [a constrained]\n            (when constrained\n              (let [x (first constrained)]\n                (if (and (lvar? x)\n                         (and (lvar? (walk a x))\n                              (nil? (get-dom a x ::fd))))\n                  (throw (Exception. (str \"Constrained variable \" x \" without domain\")))\n                  (recur a (next constrained))))))]\n    (verify-all-bound* a (seq constrained))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic",
    "name" "remcg",
    "column" 1,
    "line" 2201,
    "source"
    "(defn remcg [c]\n  (fn [a]\n    (assoc a :cs (remc (:cs a) a c))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "->AnswerCache",
    "line" 1972,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.AnswerCache.",
    "tag" nil,
    "source"
    "(deftype AnswerCache [ansl anss _meta]\n  Object\n  (toString [this]\n    (str \"<answer-cache:\" (pr-str ansl) \">\"))\n\n  clojure.lang.IObj\n  (meta [_] _meta)\n  (withMeta [_ new-meta]\n    (AnswerCache. ansl anss new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :ansl ansl\n      :anss anss\n      not-found))\n\n  IAnswerCache\n  (-add [this x]\n    (AnswerCache. (conj ansl x) (conj anss x) _meta))\n  (-cached? [_ x]\n    (let [^clojure.lang.IPersistentSet anss anss]\n      (.contains anss x))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["ansl" "anss" "_meta"]]}
   {"ns" "clojure.core.logic",
    "name" "project",
    "macro" true,
    "line" 1268,
    "column" 1,
    "doc"
    "Extract the values bound to the specified logic vars. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro project\n  \"Extract the values bound to the specified logic vars. Non-relational.\"\n  [[& vars] & goals]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (let [~@(project-bindings vars a)]\n         ((fresh []\n            ~@goals) ~a)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [[["&" "vars"] "&" "goals"]]}
   {"arglists" [["x" "k"]],
    "ns" "clojure.core.logic",
    "name" "dissoc-meta",
    "column" 1,
    "line" 21,
    "source"
    "(defn dissoc-meta [x k]\n  (with-meta x (dissoc (meta x) k)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "distincto",
    "line" 2591,
    "column" 1,
    "doc"
    "A relation which guarantees no element of l will unify\n   with another element of l.",
    "tag" nil,
    "source"
    "(defne distincto\n  \"A relation which guarantees no element of l will unify\n   with another element of l.\"\n  [l]\n  ([()])\n  ([[h]])\n  ([[h0 h1 . t]]\n     (!= h0 h1)\n     (distincto (lcons h0 t))\n     (distincto (lcons h1 t))))",
    "file" "clojure/core/logic.clj",
    "arglists" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic",
    "name" "empty-s",
    "column" 1,
    "line" 522,
    "source" "(def empty-s (make-s))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "nilo",
    "line" 1590,
    "column" 1,
    "doc" "A relation where a is nil",
    "tag" nil,
    "source"
    "(defn nilo\n  \"A relation where a is nil\"\n  [a]\n  (== nil a))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a"]]}
   {"ns" "clojure.core.logic",
    "name" "fna",
    "macro" true,
    "line" 1659,
    "column" 1,
    "doc" "Define an anonymous soft cut goal. See conda.",
    "tag" nil,
    "source"
    "(defmacro fna\n  \"Define an anonymous soft cut goal. See conda.\"\n  [& rest]\n  `(fnm conda ~@rest))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "rest"]]}
   {"arglists" [["n"]],
    "ns" "clojure.core.logic",
    "name" "lvars",
    "column" 1,
    "line" 705,
    "source" "(defn lvars [n]\n  (repeatedly n lvar))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "run*",
    "macro" true,
    "line" 1201,
    "column" 1,
    "doc" "Executes goals until results are exhausted.",
    "tag" nil,
    "source"
    "(defmacro run*\n  \"Executes goals until results are exhausted.\"\n  [bindings & goals]\n  `(-run {:occurs-check true :n false} ~bindings ~@goals))",
    "file" "clojure/core/logic.clj",
    "arglists" [["bindings" "&" "goals"]]}
   {"ns" "clojure.core.logic",
    "name" "fnm",
    "macro" true,
    "line" 1573,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro fnm\n   {:arglists '([t as tabled? & cs])}\n   [t as & cs]\n  (if-let [cs (and (= (first cs) :tabled) (rest cs))]\n    `(-fnm tabled ~t ~as ~@cs)\n    `(-fnm fn ~t ~as ~@cs)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["t" "as" "tabled?" "&" "cs"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "lcons-p?",
    "line" 1414,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- lcons-p? [p]\n  (and (coll? p)\n       (not (nil? (some '#{.} p)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["p"]]}
   {"ns" "clojure.core.logic",
    "name" "conda",
    "macro" true,
    "line" 1358,
    "column" 1,
    "doc"
    "Soft cut. Once the head of a clause has succeeded\n  all other clauses will be ignored. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro conda\n  \"Soft cut. Once the head of a clause has succeeded\n  all other clauses will be ignored. Non-relational.\"\n  [& clauses]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (ifa* ~@(map (cond-clauses a) clauses)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "clauses"]]}
   {"ns" "clojure.core.logic",
    "name" "everyg",
    "line" 1618,
    "column" 1,
    "doc"
    "A pseudo-relation that takes a coll and ensures that the goal g\n   succeeds on every element of the collection.",
    "tag" nil,
    "source"
    "(defn everyg\n  \"A pseudo-relation that takes a coll and ensures that the goal g\n   succeeds on every element of the collection.\"\n  [g coll]\n  (fn [a]\n    (let [coll (walk a coll)]\n      (((fn everyg* [g coll]\n          (if (seq coll)\n            (all\n             (g (first coll))\n             (everyg* g (next coll)))\n            s#)) g coll) a))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["g" "coll"]]}
   {"arglists" [["u" "v" "s"]],
    "ns" "clojure.core.logic",
    "name" "unify-with-pmap*",
    "column" 1,
    "line" 2616,
    "source"
    "(defn unify-with-pmap* [u v s]\n  (loop [ks (keys u) s s]\n    (if (seq ks)\n      (let [kf (first ks)\n            vf (get v kf ::not-found)]\n        (if (= vf ::not-found)\n          nil\n          (let [uf (get u kf)]\n            (if (lvar? vf)\n              (recur (next ks) ((featurec vf uf) s))\n              (if (map? uf)\n                (if-let [s (unify s (partial-map uf) vf)]\n                  (recur (next ks) s))\n                (if-let [s (unify s uf vf)]\n                  (recur (next ks) s)\n                  nil))))))\n      s)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists"
    [["s" "x" "dom" "domv"] ["s" "x" "dom" "domv" "seenset"]],
    "ns" "clojure.core.logic",
    "name" "add-dom",
    "column" 1,
    "line" 452,
    "source"
    "(defn add-dom\n  ([s x dom domv]\n     (let [x (root-var s x)]\n       (add-dom s x dom domv nil)))\n  ([s x dom domv seenset]\n     (let [v (root-val s x)\n           s (if (subst-val? v)\n               (update-var s x (assoc-dom v dom domv))\n               (let [v (if (lvar? v) ::unbound v)]\n                 (ext-no-check s x (subst-val v {dom domv}))))]\n       (sync-eset s v seenset\n         (fn [s y] (add-dom s y dom domv (conj (or seenset #{}) x)))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "->Pair",
    "line" 36,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.Pair.",
    "tag" nil,
    "source"
    "(deftype Pair [lhs rhs]\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :lhs lhs\n      :rhs rhs\n      not-found))\n\n  clojure.lang.Counted\n  (count [_] 2)\n\n  clojure.lang.Indexed\n  (nth [_ i] (case i\n                   0 lhs\n                   1 rhs\n                   (throw (IndexOutOfBoundsException.))))\n  (nth [_ i not-found] (case i\n                             0 lhs\n                             1 rhs\n                             not-found))\n\n  java.util.Map$Entry\n  (getKey [_] lhs)\n  (getValue [_] rhs)\n\n  Object\n  (toString [_]\n    (str \"(\" lhs \" . \" rhs \")\"))\n\n  (equals [_ o]\n    (if (instance? Pair o)\n      (and (= lhs (:lhs o))\n           (= rhs (:rhs o)))\n      false)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["lhs" "rhs"]]}
   {"ns" "clojure.core.logic",
    "name" "fnu",
    "macro" true,
    "line" 1664,
    "column" 1,
    "doc" "Define an anonymous committed choice goal. See condu.",
    "tag" nil,
    "source"
    "(defmacro fnu\n  \"Define an anonymous committed choice goal. See condu.\"\n  [& rest]\n  `(fnm condu ~@rest))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "clojure.core.logic",
    "name" "featurec",
    "line" 2692,
    "column" 1,
    "doc"
    "Ensure that a map contains at least the key-value pairs\n  in the map fs. fs must be partially instantiated - that is, \n  it may contain values which are logic variables to support \n  feature extraction.",
    "tag" nil,
    "source"
    "(defn featurec\n  \"Ensure that a map contains at least the key-value pairs\n  in the map fs. fs must be partially instantiated - that is, \n  it may contain values which are logic variables to support \n  feature extraction.\"\n  [x fs]\n  (cgoal (-featurec x (partial-map fs))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["x" "fs"]]}
   {"ns" "clojure.core.logic",
    "name" "is",
    "macro" true,
    "line" 1285,
    "column" 1,
    "doc"
    "Set the value of a var to value of another var with the operation\n   applied. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro is\n  \"Set the value of a var to value of another var with the operation\n   applied. Non-relational.\"\n  [u v op]\n  `(project [~v]\n     (== ~u (~op ~v))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["u" "v" "op"]]}
   {"ns" "clojure.core.logic",
    "name" "matche",
    "macro" true,
    "line" 1646,
    "column" 1,
    "doc"
    "Pattern matching macro. All patterns will be tried.\n  See conde.",
    "tag" nil,
    "source"
    "(defmacro matche\n  \"Pattern matching macro. All patterns will be tried.\n  See conde.\"\n  [xs & cs]\n  (binding [*locals* (env-locals xs (keys &env))]\n    (handle-clauses `conde xs cs)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["xs" "&" "cs"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "lvar-sym?",
    "line" 1428,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- lvar-sym? [s]\n  (and (symbol? s)\n       (not= s '.)\n       (not (contains? *locals* s))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["s"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "all-blank?",
    "line" 1517,
    "column" 1,
    "tag" nil,
    "source" "(defn- all-blank? [p]\n  (every? #(= % '_) p))",
    "file" "clojure/core/logic.clj",
    "arglists" [["p"]]}
   {"ns" "clojure.core.logic",
    "name" "lvaro",
    "macro" true,
    "line" 1389,
    "column" 1,
    "doc"
    "A goal that succeeds if the argument is fresh. v must be a logic\n  variable. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro lvaro\n  \"A goal that succeeds if the argument is fresh. v must be a logic\n  variable. Non-relational.\"\n  [v]\n  `(fn [a#]\n     (if (lvar? (walk a# ~v))\n       a# nil)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["v"]]}
   {"arglists" [["a" "lvar"]],
    "ns" "clojure.core.logic",
    "name" "trace-lvar",
    "column" 1,
    "line" 1243,
    "source"
    "(defn trace-lvar [a lvar]\n  `(println (format \"%5s = %s\" (str '~lvar) (-reify ~a ~lvar))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "u" "v"]],
    "ns" "clojure.core.logic",
    "name" "unify",
    "column" 1,
    "line" 221,
    "source"
    "(defn unify [s u v]\n  (if (identical? u v)\n    s\n    (let [u  (walk s u)\n          v  (walk s v)]\n      ;; TODO: we can't use an identical? check here at the moment\n      ;; because we add metadata on vars in walk - David\n      (if (and (lvar? u) (= u v))\n        s\n        (if (and (not (lvar? u)) (lvar? v))\n          (unify-terms v u s)\n          (unify-terms u v s))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "fresh",
    "macro" true,
    "line" 1168,
    "column" 1,
    "doc"
    "Creates fresh variables. Goals occuring within form a logical \n  conjunction.",
    "tag" nil,
    "source"
    "(defmacro fresh\n  \"Creates fresh variables. Goals occuring within form a logical \n  conjunction.\"\n  [[& lvars] & goals]\n  `(fn [a#]\n     (-inc\n      (let [~@(lvar-binds lvars)]\n        (bind* a# ~@goals)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [[["&" "lvars"] "&" "goals"]]}
   {"arglists" [["x" "k"]],
    "ns" "clojure.core.logic",
    "name" "dissoc-dom",
    "column" 1,
    "line" 27,
    "source"
    "(defn dissoc-dom [x k]\n  (assoc x :doms (dissoc (:doms x) k)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "def-arity-exc-helper",
    "macro" true,
    "line" 1741,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro def-arity-exc-helper []\n  (try\n    (Class/forName \"clojure.lang.ArityException\")\n    `(defn arity-exc-helper [~'name ~'n]\n       (fn [~'& ~'args]\n         (throw (clojure.lang.ArityException. ~'n (str ~'name)))))\n    (catch java.lang.ClassNotFoundException e\n     `(defn ~'arity-exc-helper [~'name ~'n]\n        (fn [~'& ~'args]\n          (throw\n           (java.lang.IllegalArgumentException.\n            (str \"Wrong number of args (\" ~'n \") passed to:\" ~'name))))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [[]]}
   {"ns" "clojure.core.logic",
    "name" "membero",
    "line" 1696,
    "column" 1,
    "doc"
    "A relation where l is a collection, such that l contains x.",
    "tag" nil,
    "source"
    "(defne membero\n  \"A relation where l is a collection, such that l contains x.\"\n  [x l]\n  ([_ [x . tail]])\n  ([_ [head . tail]]\n    (membero x tail)))",
    "file" "clojure/core/logic.clj",
    "arglists" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "partial-map?",
    "column" 1,
    "line" 2660,
    "source" "(defn partial-map? [x]\n  (instance? PMap x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "appendo",
    "line" 1713,
    "column" 1,
    "doc"
    "A relation where x, y, and z are proper collections, \n  such that z is x appended to y",
    "tag" nil,
    "source"
    "(defne appendo \n  \"A relation where x, y, and z are proper collections, \n  such that z is x appended to y\"\n  [x y z]\n  ([() _ y])\n  ([[a . d] _ [a . r]] (appendo d y r)))",
    "file" "clojure/core/logic.clj",
    "arglists" nil}
   {"arglists" [[]],
    "ns" "clojure.core.logic",
    "name" "make-cs",
    "column" 1,
    "line" 173,
    "source" "(defn make-cs []\n  (ConstraintStore. {} {} 0 #{}))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "project-bindings",
    "line" 1265,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- project-bindings [vars s]\n  (reduce concat (map (project-binding s) vars)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["vars" "s"]]}
   {"ns" "clojure.core.logic",
    "name" "->Rel",
    "line" 1840,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.Rel.",
    "tag" nil,
    "source" "(RelHelper 20)",
    "file" "clojure/core/logic.clj",
    "arglists"
    [["name"
      "indexes"
      "meta"
      "f1"
      "f2"
      "f3"
      "f4"
      "f5"
      "f6"
      "f7"
      "f8"
      "f9"
      "f10"
      "f11"
      "f12"
      "f13"
      "f14"
      "f15"
      "f16"
      "f17"
      "&"
      "overage"]]}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic",
    "name" "runcg",
    "column" 1,
    "line" 2205,
    "source"
    "(defn runcg [c]\n  (fn [a]\n    (assoc a :cs (runc (:cs a) c true))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [[] ["g0" "g1"]],
    "ns" "clojure.core.logic",
    "name" "composeg",
    "column" 1,
    "line" 1029,
    "source"
    "(defn composeg\n  ([] identity)\n  ([g0 g1]\n     (fn [a]\n       (let [a (g0 a)]\n         (and a (g1 a))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "lcons?",
    "column" 1,
    "line" 851,
    "source" "(defn lcons? [x]\n  (instance? LCons x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "u" "v"]],
    "ns" "clojure.core.logic",
    "name" "occurs-check",
    "column" 1,
    "line" 201,
    "source"
    "(defn occurs-check [s u v]\n  (let [v (walk s v)]\n    (occurs-check-term v u s)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "succeed",
    "line" 1116,
    "column" 1,
    "doc" "A goal that always succeeds.",
    "tag" nil,
    "source"
    "(defn succeed\n  \"A goal that always succeeds.\"\n  [a] a)",
    "file" "clojure/core/logic.clj",
    "arglists" [["a"]]}
   {"ns" "clojure.core.logic",
    "name" "emptyo",
    "line" 1595,
    "column" 1,
    "doc" "A relation where a is the empty list",
    "tag" nil,
    "source"
    "(defn emptyo\n  \"A relation where a is the empty list\"\n  [a]\n  (== '() a))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a"]]}
   {"arglists" [["s" "v"] ["s" "v" "r"]],
    "ns" "clojure.core.logic",
    "name" "-reify",
    "column" 1,
    "line" 248,
    "source"
    "(defn -reify\n  ([s v]\n     (let [v (walk* s v)]\n       (walk* (-reify* (with-meta empty-s (meta s)) v) v)))\n  ([s v r]\n     (let [v (walk* s v)]\n       (walk* (-reify* r v) v))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "waiting-stream-check",
    "line" 2017,
    "column" 1,
    "doc"
    "Take a waiting stream, a success continuation, and a failure continuation.\n   If we don't find any ready suspended streams, invoke the failure continuation. \n   If we find a ready suspended stream calculate the remainder of the waiting\n   stream. If we've reached the fixpoint just call the thunk of the suspended\n   stream, otherwise call mplus on the result of the thunk and the remainder\n   of the waiting stream. Pass this result to the success contination.",
    "tag" nil,
    "source"
    "(defn waiting-stream-check\n  \"Take a waiting stream, a success continuation, and a failure continuation.\n   If we don't find any ready suspended streams, invoke the failure continuation. \n   If we find a ready suspended stream calculate the remainder of the waiting\n   stream. If we've reached the fixpoint just call the thunk of the suspended\n   stream, otherwise call mplus on the result of the thunk and the remainder\n   of the waiting stream. Pass this result to the success contination.\"\n  [w success-cont failure-cont]\n  (loop [w w a []]\n    (cond\n     (nil? w) (failure-cont)\n\n     (ready? (first w))\n     (success-cont\n       (fn []\n         (let [ss (first w)\n               f  (:f ss)\n               w  (into a (next w))]\n           (if (empty? w)\n             (f)\n             (mplus (f) (fn [] w))))))\n\n     :else (recur (next w) (conj a (first w))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["w" "success-cont" "failure-cont"]]}
   {"arglists" [["cs" "x" "c"]],
    "ns" "clojure.core.logic",
    "name" "add-var",
    "column" 1,
    "line" 163,
    "source"
    "(defn add-var [cs x c]\n  (when-not (lvar? x)\n    (throw (Error. (str \"constraint store assoc expected logic var key: \" x))))\n  (let [cm (:cm cs)\n        km (:km cs)\n        cid (:cid cs)\n        nkm (update-in km [x] (fnil (fn [s] (conj s cid)) #{}))\n        ncm (assoc cm cid c)]\n    (ConstraintStore. nkm ncm cid (:running cs))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "ifa*",
    "macro" true,
    "line" 1302,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ifa*\n  ([])\n  ([[e & gs] & grest]\n     `(ifa ~e [~@gs]\n           ~(if (seq grest)\n              `(delay (ifa* ~@grest))\n              nil))))",
    "file" "clojure/core/logic.clj",
    "arglists" [[] [["e" "&" "gs"] "&" "grest"]]}
   {"ns" "clojure.core.logic",
    "name" "-inc",
    "macro" true,
    "line" 1053,
    "column" 1,
    "tag" nil,
    "source" "(defmacro -inc [& rest]\n  `(fn -inc [] ~@rest))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "rest"]]}
   {"arglists" [["name" "n"]],
    "ns" "clojure.core.logic",
    "name" "arity-exc-helper",
    "column" 1,
    "line" 1754,
    "source" "(def-arity-exc-helper)",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "set-sym",
    "line" 1845,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- set-sym [name arity]\n  (->sym name \"_\" arity \"-set\"))",
    "file" "clojure/core/logic.clj",
    "arglists" [["name" "arity"]]}
   {"arglists" [["c" "args"]],
    "ns" "clojure.core.logic",
    "name" "-nafc",
    "column" 1,
    "line" 2814,
    "source"
    "(defn -nafc\n  ([c args]\n    (reify\n      IConstraintStep\n      (-step [this s]\n        (reify\n           clojure.lang.IFn\n           (invoke [_ s]\n             (when-not (tramp ((apply c args) s))\n               ((remcg this) s)))\n           IRunnable\n           (-runnable? [_]\n             (every? #(ground-term? % s) args))))\n      IConstraintOp\n      (-rator [_]\n        `nafc)\n      (-rands [_]\n        (vec (concat [c] args)))\n      IReifiableConstraint\n      (-reifyc [_ v r s]\n        `(nafc ~c ~@(-reify s args r)))\n      IConstraintWatchedStores\n      (-watched-stores [this] #{::subst}))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "trace-lvars",
    "macro" true,
    "line" 1246,
    "column" 1,
    "doc" "Goal for tracing the values of logic variables.",
    "tag" nil,
    "source"
    "(defmacro trace-lvars\n  \"Goal for tracing the values of logic variables.\"\n  [title & lvars]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (println ~title)\n       ~@(map (partial trace-lvar a) lvars)\n       ~a)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["title" "&" "lvars"]]}
   {"ns" "clojure.core.logic",
    "name" "defnc",
    "macro" true,
    "line" 2766,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro defnc [name args & body]\n  `(def ~name (fnc ~args ~@body)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["name" "args" "&" "body"]]}
   {"ns" "clojure.core.logic",
    "name" "let-dom",
    "macro" true,
    "line" 2363,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro let-dom\n  [a vars & body]\n  (let [get-var-dom (fn [a [v b]]\n                      `(~b (get-dom-fd ~a ~v)))]\n   `(let [~@(mapcat (partial get-var-dom a) (partition 2 vars))]\n      ~@body)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a" "vars" "&" "body"]]}
   {"ns" "clojure.core.logic",
    "name" "uai",
    "macro" true,
    "line" 723,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro uai\n  [& args]\n  (if (resolve 'unchecked-add-int)\n    `(unchecked-add-int ~@args)\n    `(unchecked-add ~@args)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "args"]]}
   {"arglists" nil,
    "ns" "clojure.core.logic",
    "name" "s#",
    "column" 1,
    "line" 1124,
    "source" "(def s# succeed)",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "partial-map",
    "line" 2654,
    "column" 1,
    "doc"
    "Given map m, returns partial map that unifies with maps even if it\n   doesn't share all of the keys of that map.",
    "tag" nil,
    "source"
    "(defn partial-map\n  \"Given map m, returns partial map that unifies with maps even if it\n   doesn't share all of the keys of that map.\"\n  [m]\n  (map->PMap m))",
    "file" "clojure/core/logic.clj",
    "arglists" [["m"]]}
   {"arglists" [["v" "x" "a"]],
    "ns" "clojure.core.logic",
    "name" "sort-by-strategy",
    "column" 1,
    "line" 2375,
    "source"
    "(defn sort-by-strategy [v x a]\n  (case (-> x meta ::strategy)\n    ::ff (seq (sort (sort-by-member-count a) v))\n    ;; TODO: throw on non-existant strategies\n    v))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "conso",
    "line" 1600,
    "column" 1,
    "doc"
    "A relation where l is a collection, such that a is the first of l \n  and d is the rest of l. If ground d must be bound to a proper tail.",
    "tag" nil,
    "source"
    "(defn conso\n  \"A relation where l is a collection, such that a is the first of l \n  and d is the rest of l. If ground d must be bound to a proper tail.\"\n  [a d l]\n  (== (lcons a d) l))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a" "d" "l"]]}
   {"ns" "clojure.core.logic",
    "name" "map->PMap",
    "line" 2636,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.logic.PMap, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord PMap []\n  INonStorable\n\n  IUnifyTerms\n  (unify-terms [u v s]\n    (if (map? v)\n      (unify-with-pmap* u v s)\n      nil))\n\n  IUnifyWithRecord\n  (unify-with-record [u v s]\n    (if (map? v)\n      (unify-with-pmap* u v s)\n      nil))\n\n  IUninitialized\n  (-uninitialized [_] (PMap.)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["m__5818__auto__"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "lvar-binds",
    "line" 1165,
    "column" 1,
    "tag" nil,
    "source" "(defn- lvar-binds [syms]\n  (mapcat lvar-bind syms))",
    "file" "clojure/core/logic.clj",
    "arglists" [["syms"]]}
   {"ns" "clojure.core.logic",
    "name" "log",
    "macro" true,
    "line" 1231,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro log [& s]\n  \"Goal for println\"\n  `(fn [a#]\n     (println ~@s)\n     a#))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "s"]]}
   {"arglists" [["s" "x" "attr"]],
    "ns" "clojure.core.logic",
    "name" "rem-attr",
    "column" 1,
    "line" 425,
    "source"
    "(defn rem-attr [s x attr]\n  (let [x (root-var s x)\n        v (root-val s x)]\n    (if (subst-val? v)\n      (let [new-meta (dissoc (meta v) attr)]\n        (if (and (zero? (count new-meta)) (not= (:v v) ::unbound))\n          (update-var s x (:v v))\n          (update-var s x (with-meta v new-meta))))\n      s)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "permuteo",
    "line" 1722,
    "column" 1,
    "doc"
    "A relation that will permute xl into the yl. May not\n   terminate if xl is not ground.",
    "tag" nil,
    "source"
    "(defne permuteo\n  \"A relation that will permute xl into the yl. May not\n   terminate if xl is not ground.\"\n  [xl yl]\n  ([() ()])\n  ([[x . xs] _]\n     (fresh [ys]\n      (permuteo xs ys)\n      (rembero x yl ys))))",
    "file" "clojure/core/logic.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic",
    "name" "->ConstraintStore",
    "line" 102,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.ConstraintStore.",
    "tag" nil,
    "source"
    "(deftype ConstraintStore [km cm cid running]\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :km km\n      :cm cm\n      :cid cid\n      :running running\n      not-found))\n\n  IConstraintStore\n  (addc [this a c]\n    (let [vars (var-rands a c)\n          c (with-id c cid)\n          cs (reduce (fn [cs v] (add-var cs v c)) this vars)]\n      (ConstraintStore. (:km cs) (:cm cs) (inc cid) running)))\n\n  (updatec [this a c]\n    (let [oc (cm (id c))\n          nkm (if (instance? clojure.core.logic.protocols.IEntailedVar c)\n                (reduce (fn [km x]\n                          (if (-entailed-var? c x)\n                            (dissoc km x)\n                            km))\n                        km (var-rands a oc))\n                km)]\n      (ConstraintStore. nkm (assoc cm (id c) c) cid running)))\n\n  (remc [this a c]\n    (let [vs (var-rands a c)\n          ocid (id c)\n          nkm (reduce (fn [km v]\n                        (let [vcs (disj (get km v) ocid)]\n                          (if (empty? vcs)\n                            (dissoc km v)\n                            (assoc km v vcs))))\n                      km vs)\n          ncm (dissoc cm ocid)]\n      (ConstraintStore. nkm ncm cid running)))\n\n  (runc [this c state]\n    (if state\n      (ConstraintStore. km cm cid (conj running (id c)))\n      (ConstraintStore. km cm cid (disj running (id c)))))\n\n  (constraints-for [this a x ws]\n    (when-let [ids (get km (root-var a x))]\n      (filter #((-watched-stores %) ws) (map cm (remove running ids)))))\n\n  (migrate [this x root]\n    (let [xcs    (km x)\n          rootcs (km root #{})\n          nkm    (assoc (dissoc km x) root (into rootcs xcs))]\n      (ConstraintStore. nkm cm cid running)))\n\n  clojure.lang.Counted\n  (count [this]\n    (count cm)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["km" "cm" "cid" "running"]]}
   {"arglists" [["s"]],
    "ns" "clojure.core.logic",
    "name" "reify-lvar-name",
    "column" 1,
    "line" 238,
    "source"
    "(defn reify-lvar-name [s]\n  (let [c (count s)]\n    (if (< c 100)\n      (unbound-names c)\n      (symbol (str \"_\" (count s))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "condu",
    "macro" true,
    "line" 1366,
    "column" 1,
    "doc"
    "Committed choice. Once the head (first goal) of a clause \n  has succeeded, remaining goals of the clause will only\n  be run once. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro condu\n  \"Committed choice. Once the head (first goal) of a clause \n  has succeeded, remaining goals of the clause will only\n  be run once. Non-relational.\"\n  [& clauses]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (ifu* ~@(map (cond-clauses a) clauses)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "clauses"]]}
   {"ns" "clojure.core.logic",
    "name" "copy-term",
    "line" 1380,
    "column" 1,
    "doc" "Copies a term u into v. Non-relational.",
    "tag" nil,
    "source"
    "(defn copy-term\n  \"Copies a term u into v. Non-relational.\"\n  [u v]\n  (project [u]\n    (== (walk* (build empty-s u) u) v)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["u" "v"]]}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic",
    "name" "run-constraint",
    "column" 1,
    "line" 2222,
    "source"
    "(defn run-constraint [c]\n  (fn [a]\n    (let [c' (-step c a)]\n      (if (or (not (ientailed? c'))\n              (not (entailed? c c' a)))\n        (if (-runnable? c')\n          ((composeg* (runcg c) c' (stopcg c)) a)\n          a)\n        ((remcg c) a)))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "defna",
    "macro" true,
    "line" 1669,
    "column" 1,
    "doc" "Define a soft cut goal. See conda.",
    "tag" nil,
    "source"
    "(defmacro defna\n  \"Define a soft cut goal. See conda.\"\n  [& rest]\n  `(defnm conda ~@rest))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "clojure.core.logic",
    "name" "member1o",
    "line" 1703,
    "column" 1,
    "doc"
    "Like membero but uses to disequality further constraining\n   the results. For example, if x and l are ground and x occurs \n   multiple times in l, member1o will succeed only once.",
    "tag" nil,
    "source"
    "(defne member1o\n  \"Like membero but uses to disequality further constraining\n   the results. For example, if x and l are ground and x occurs \n   multiple times in l, member1o will succeed only once.\"\n  [x l]\n  ([_ [x . tail]])\n  ([_ [head . tail]]\n    (!= x head)\n    (member1o x tail)))",
    "file" "clojure/core/logic.clj",
    "arglists" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic",
    "name" "fk",
    "column" 1,
    "line" 13,
    "source" "(def fk (Exception.))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["a"]],
    "ns" "clojure.core.logic",
    "name" "enforceable-constrained",
    "column" 1,
    "line" 2292,
    "source"
    "(defn enforceable-constrained [a]\n  (let [cs (:cs a)\n        km (:km cs)\n        cm (:cm cs)\n        vs (keys km)]\n    (filter (fn [v]\n              (some (fn [cid]\n                      (when-let [c (get cm cid)]\n                        (enforceable? c)))\n                    (get km v)))\n            vs)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["aseq"]],
    "ns" "clojure.core.logic",
    "name" "to-stream",
    "column" 1,
    "line" 1735,
    "source"
    "(defn to-stream [aseq]\n  (let [aseq (drop-while nil? aseq)]\n    (when (seq aseq)\n      (choice (first aseq)\n              (fn [] (to-stream (next aseq)))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic",
    "name" "addcg",
    "column" 1,
    "line" 2190,
    "source"
    "(defn addcg [c]\n  (fn [a]\n    (let [a (reduce (fn [a x]\n                      (ext-no-check a x (subst-val ::unbound)))\n              a (unbound-rands a c))]\n      (assoc a :cs (addc (:cs a) a c)))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "contains-lvar?",
    "column" 1,
    "line" 1850,
    "source"
    "(defn contains-lvar? [x]\n  (some lvar? (tree-seq coll? seq x)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["xs" "cs" "ws"]],
    "ns" "clojure.core.logic",
    "name" "run-constraints*",
    "column" 1,
    "line" 2267,
    "source"
    "(defn run-constraints* [xs cs ws]\n  (if (or (zero? (count cs))\n          (nil? (seq xs)))\n    s#\n    (fn [a]\n      (let [xcs (constraints-for cs a (first xs) ws)]\n        (if (seq xcs)\n          ((composeg*\n            (run-constraints xcs)\n            (run-constraints* (next xs) cs ws)) a)\n          ((run-constraints* (next xs) cs ws) a))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "defnu",
    "macro" true,
    "line" 1674,
    "column" 1,
    "doc" "Define a committed choice goal. See condu.",
    "tag" nil,
    "source"
    "(defmacro defnu\n  \"Define a committed choice goal. See condu.\"\n  [& rest]\n  `(defnm condu ~@rest))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "clojure.core.logic",
    "name" "->SubstValue",
    "line" 182,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.SubstValue.",
    "tag" nil,
    "source"
    "(defrecord SubstValue [v doms eset]\n  Object\n  (toString [_]\n    (str v)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["v" "doms" "eset"]]}
   {"ns" "clojure.core.logic",
    "name" "run",
    "macro" true,
    "line" 1196,
    "column" 1,
    "doc" "Executes goals until a maximum of n results are found.",
    "tag" nil,
    "source"
    "(defmacro run\n  \"Executes goals until a maximum of n results are found.\"\n  [n bindings & goals]\n  `(-run {:occurs-check true :n ~n} ~bindings ~@goals))",
    "file" "clojure/core/logic.clj",
    "arglists" [["n" "bindings" "&" "goals"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "fresh-expr?",
    "line" 1479,
    "column" 1,
    "tag" nil,
    "source" "(defn- fresh-expr? [cs]\n  (= (first cs) `fresh))",
    "file" "clojure/core/logic.clj",
    "arglists" [["cs"]]}
   {"arglists" [["v*" "strategy"]],
    "ns" "clojure.core.logic",
    "name" "distribute",
    "column" 1,
    "line" 2435,
    "source"
    "(defn distribute [v* strategy]\n  (fn [a]\n    (add-attr a v* ::strategy ::ff)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "->LCons",
    "line" 731,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.LCons.",
    "tag" nil,
    "source"
    "(deftype LCons [a d ^{:unsynchronized-mutable true :tag int} cache meta]\n  ITreeTerm\n  clojure.lang.IObj\n  (meta [this]\n    meta)\n  (withMeta [this new-meta]\n    (LCons. a d cache new-meta))\n\n  LConsSeq\n  (lfirst [_] a)\n  (lnext [_] d)\n\n  LConsPrint\n  (toShortString [this]\n    (cond\n     (.. this getClass (isInstance d)) (str a \" \" (toShortString d))\n     :else (str a \" . \" d )))\n\n  Object\n  (toString [this] (cond\n                    (.. this getClass (isInstance d))\n                      (str \"(\" a \" \" (toShortString d) \")\")\n                    :else (str \"(\" a \" . \" d \")\")))\n\n  (equals [this o]\n    (or (identical? this o)\n        (and (.. this getClass (isInstance o))\n             (loop [me this\n                    you o]\n               (cond\n                (nil? me) (nil? you)\n                (lvar? me) true\n                (lvar? you) true\n                (and (lcons? me) (lcons? you))\n                  (let [mef  (lfirst me)\n                        youf (lfirst you)]\n                    (and (or (= mef youf)\n                             (lvar? mef)\n                             (lvar? youf))\n                         (recur (lnext me) (lnext you))))\n                :else (= me you))))))\n\n  (hashCode [this]\n    (if (= cache -1)\n      (do\n        (set! cache (uai (umi (int 31) (clojure.lang.Util/hash d))\n                         (clojure.lang.Util/hash a)))\n        cache)\n      cache))\n\n  IUnifyTerms\n  (unify-terms [u v s]\n    (cond\n      (sequential? v)\n      (loop [u u v v s s]\n        (if (seq v)\n          (if (lcons? u)\n            (if-let [s (unify s (lfirst u) (first v))]\n              (recur (lnext u) (next v) s)\n              nil)\n            (unify s u v))\n          (if (lvar? u)\n            (if-let [s (unify s u '())]\n              s\n              (unify s u nil))\n            nil)))\n      \n      (lcons? v)\n      (loop [u u v v s s]\n        (if (lvar? u)\n          (unify s u v)\n          (cond\n            (lvar? v) (unify s v u)\n            (and (lcons? u) (lcons? v))\n            (if-let [s (unify s (lfirst u) (lfirst v))]\n              (recur (lnext u) (lnext v) s)\n              nil)\n            :else (unify s u v))))\n      \n      :else nil))\n\n  IReifyTerm\n  (reify-term [v s]\n    (loop [v v s s]\n      (if (lcons? v)\n        (recur (lnext v) (-reify* s (lfirst v)))\n        (-reify* s v))))\n\n  ;; TODO: no way to make this non-stack consuming w/o a lot more thinking\n  ;; we could use continuation passing style and trampoline\n  IWalkTerm\n  (walk-term [v f]\n    (lcons (f (lfirst v))\n           (f (lnext v))))\n\n  IOccursCheckTerm\n  (occurs-check-term [v x s]\n    (loop [v v x x s s]\n      (if (lcons? v)\n        (or (occurs-check s x (lfirst v))\n            (recur (lnext v) x s))\n        (occurs-check s x v))))\n\n  IBuildTerm\n  (build-term [u s]\n    (loop [u u s s]\n      (if (lcons? u)\n        (recur (lnext u) (build s (lfirst u)))\n        (build s u)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a" "d" "cache" "meta"]]}
   {"arglists" [["s" "doms" "eset"]],
    "ns" "clojure.core.logic",
    "name" "update-eset",
    "column" 1,
    "line" 557,
    "source"
    "(defn update-eset [s doms eset]\n  (loop [eset (seq eset) s s]\n    (if eset\n      (when-let [s (merge-doms s (root-var s (first eset)) doms)]\n        (recur (next eset) s))\n      s)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "p->term",
    "line" 1440,
    "column" 1,
    "doc"
    "Convert a pattern p into a term suitable for unification. Takes an atom\n   containing a set for returning any encountered vars which will be declared\n   fresh.",
    "tag" nil,
    "source"
    "(defn- p->term\n  \"Convert a pattern p into a term suitable for unification. Takes an atom\n   containing a set for returning any encountered vars which will be declared\n   fresh.\"\n  ([p vars] (p->term p vars false))\n  ([p vars quoted]\n     (cond\n       (= p '_) `(lvar)\n       (lcons-p? p) (p->llist p vars quoted)\n       (coll? p)\n       (cond\n         ;; support simple expressions\n         (seq? p)\n         (let [[f s] p]\n           (cond\n             (= f 'quote)\n             (if (and (seq? s) (not quoted))\n               (p->term s vars true)\n               p) \n             (= f 'clojure.core/unquote)\n             (if quoted\n               (update-pvars! s vars)\n               (throw (Exception. \"Invalid use of clojure.core/unquote in pattern.\")))\n             :else\n             (let [ps (map #(p->term % vars quoted) p)]\n               (if quoted\n                 `(list ~@ps)\n                 ps))))\n         ;; preserve original collection type\n         :else\n         (let [ps (map #(p->term % vars quoted) p)]\n           (cond\n             (instance? clojure.lang.MapEntry p) (into [] ps)\n             :else (into (empty p) ps))))\n       (symbol? p) (if quoted\n                     (list 'quote p)\n                     (update-pvars! p vars))\n       :else p)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["p" "vars"] ["p" "vars" "quoted"]]}
   {"arglists" nil,
    "ns" "clojure.core.logic",
    "name" "f-sym",
    "column" 1,
    "line" 1759,
    "source" "(def f-sym (partial sym-helper \"f\"))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "llist",
    "macro" true,
    "line" 854,
    "column" 1,
    "doc"
    "Constructs a sequence from 2 or more arguments, with the last argument as the\n   tail. The tail is improper if the last argument is a logic variable.",
    "tag" nil,
    "source"
    "(defmacro llist\n  \"Constructs a sequence from 2 or more arguments, with the last argument as the\n   tail. The tail is improper if the last argument is a logic variable.\"\n  ([f s] `(lcons ~f ~s))\n  ([f s & rest] `(lcons ~f (llist ~s ~@rest))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["f" "s"] ["f" "s" "&" "rest"]]}
   {"ns" "clojure.core.logic",
    "name" "bind*",
    "macro" true,
    "line" 1043,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro bind*\n  ([a g] `(bind ~a ~g))\n  ([a g & g-rest]\n     `(bind* (bind ~a ~g) ~@g-rest)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a" "g"] ["a" "g" "&" "g-rest"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "bind-conde-clauses",
    "line" 1148,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- bind-conde-clauses [a clauses]\n  (map (bind-conde-clause a) clauses))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a" "clauses"]]}
   {"arglists" [["x" "k" "v"]],
    "ns" "clojure.core.logic",
    "name" "assoc-dom",
    "column" 1,
    "line" 24,
    "source"
    "(defn assoc-dom [x k v]\n  (assoc x :doms (assoc (:doms x) k v)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "run-nc",
    "macro" true,
    "line" 1206,
    "column" 1,
    "doc"
    "Executes goals until a maximum of n results are found. Does not \n   occurs-check.",
    "tag" nil,
    "source"
    "(defmacro run-nc\n  \"Executes goals until a maximum of n results are found. Does not \n   occurs-check.\"\n  [n bindings & goals]\n  `(-run {:occurs-check false :n ~n} ~bindings ~@goals))",
    "file" "clojure/core/logic.clj",
    "arglists" [["n" "bindings" "&" "goals"]]}
   {"arglists" [["x" "s"]],
    "ns" "clojure.core.logic",
    "name" "ground-term?",
    "column" 1,
    "line" 2703,
    "source"
    "(defn ground-term? [x s]\n  (letfn [(-ground-term? [x s]\n            (let [x (walk s x)]\n              (if (lvar? x)\n                (throw fk)\n                (walk-term x\n                  (fn [x]\n                    (let [x (walk s x)]\n                      (cond\n                        (lvar? x) (throw fk)\n                        (tree-term? x) (-ground-term? x s)\n                        :else x)))))))]\n    (try\n      (-ground-term? x s)\n      true\n      (catch Exception e\n        false))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x" "vars"]],
    "ns" "clojure.core.logic",
    "name" "update-pvars!",
    "column" 1,
    "line" 1433,
    "source"
    "(defn update-pvars! [x vars]\n  (if (lvar-sym? x)\n    (do\n      (swap! vars conj x)\n      x)\n    x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "x" "root"]],
    "ns" "clojure.core.logic",
    "name" "merge-with-root",
    "column" 1,
    "line" 564,
    "source"
    "(defn merge-with-root [s x root]\n  (let [xv    (root-val s x)\n        rootv (root-val s root)\n        eset  (set/union (:eset rootv) (:eset xv))\n        doms (loop [xd (seq (:doms xv)) rd (:doms rootv) r {}]\n               (if xd\n                 (let [[xk xv] (first xd)]\n                   (if-let [[_ rv] (find rd xk)]\n                     (let [nd (-merge-doms xv rv)]\n                       (when nd\n                         (recur (next xd)\n                           (dissoc rd xk) (assoc r xk nd))))\n                     (recur (next xd) rd (assoc r xk xv))))\n                 (merge r rd)))\n        nv (when doms\n             (subst-val (:v rootv) doms eset\n               (merge (meta xv) (meta rootv))))]\n    (when nv\n      (-> s\n        (ext-no-check root nv)\n        (update-eset doms eset)))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["t" "fc"]],
    "ns" "clojure.core.logic",
    "name" "constrain-tree",
    "column" 1,
    "line" 2873,
    "source"
    "(defn constrain-tree [t fc]\n  (fn [a]\n    (-constrain-tree t fc a)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "handle-clause",
    "line" 1520,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- handle-clause [as]\n  (when-not (vector? as)\n    (throw (Exception. (str \"Expecting vector of arguments, instead \" as))))\n  (fn [[p & exprs]]\n    (when-not (vector? p)\n      (throw (Exception. (str \"Expecting vector of matches, instead \" p))))\n    (when-not (= (count p) (count as))\n      (warn \"Differing number of matches. Matching\" p \"against\" as))\n    (let [pas (partition 2 (interleave p as))\n          r (ex* pas exprs #{})]\n      (if (all-blank? p)\n        r\n        (list r)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["as"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "bind-conde-clause",
    "line" 1144,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- bind-conde-clause [a]\n  (fn [g-rest]\n    `(bind* ~a ~@g-rest)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "record?",
    "column" 1,
    "line" 30,
    "source"
    "(defn record? [x]\n  (instance? clojure.lang.IRecord x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "p->llist",
    "line" 1418,
    "column" 1,
    "doc"
    "Take an lcons pattern and convert it into a llist constructor\n   expression.",
    "tag" nil,
    "source"
    "(defn- p->llist\n  \"Take an lcons pattern and convert it into a llist constructor\n   expression.\"\n  ([p vars] (p->llist p vars false))\n  ([p vars quoted]\n     `(llist\n       ~@(doall\n           (map #(p->term % vars quoted)\n                (remove #(contains? '#{.} %) p))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["p" "vars"] ["p" "vars" "quoted"]]}
   {"arglists" [["g"]],
    "ns" "clojure.core.logic",
    "name" "onceo",
    "column" 1,
    "line" 1375,
    "source" "(defn onceo [g] (condu (g)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["u" "v"]],
    "ns" "clojure.core.logic",
    "name" "ext-run-csg",
    "column" 1,
    "line" 1128,
    "source"
    "(defn ext-run-csg [u v]\n  (fn [a]\n    (ext-run-cs a u v)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x" "p"] ["x" "p" "pform"]],
    "ns" "clojure.core.logic",
    "name" "predc",
    "column" 1,
    "line" 2800,
    "source"
    "(defn predc\n  ([x p] (predc x p p))\n  ([x p pform]\n     (cgoal (-predc x p pform))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "lvar-bind",
    "line" 1161,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- lvar-bind [sym]\n  ((juxt identity\n         (fn [s] `(lvar '~s))) sym))",
    "file" "clojure/core/logic.clj",
    "arglists" [["sym"]]}
   {"arglists" [["s" "x" "attr"]],
    "ns" "clojure.core.logic",
    "name" "get-attr",
    "column" 1,
    "line" 435,
    "source"
    "(defn get-attr [s x attr]\n  (let [v (root-val s x)]\n    (if (subst-val? v)\n      (-> v meta attr))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "master",
    "line" 2130,
    "column" 1,
    "doc"
    "Take the argument to the goal and check that we don't\n   have an alpha equivalent cached answer term in the cache.\n   If it doesn't already exist in the cache add the new\n   answer term.",
    "tag" nil,
    "source"
    "(defn master\n  \"Take the argument to the goal and check that we don't\n   have an alpha equivalent cached answer term in the cache.\n   If it doesn't already exist in the cache add the new\n   answer term.\"\n  [argv cache]\n  (fn [a]\n    (let [rargv (-reify a argv)]\n      (when-not (-cached? @cache rargv)\n        (swap! cache\n          (fn [cache]\n            (if (-cached? cache rargv)\n              cache\n              (-add cache (reify-tabled a argv)))))\n        a))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["argv" "cache"]]}
   {"ns" "clojure.core.logic",
    "name" "composeg*",
    "macro" true,
    "line" 1036,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro composeg*\n  ([g0] g0)\n  ([g0 & gs]\n     `(composeg\n       ~g0\n       (composeg* ~@gs))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["g0"] ["g0" "&" "gs"]]}
   {"arglists" [["s" "x" "dom"] ["s" "x" "dom" "seenset"]],
    "ns" "clojure.core.logic",
    "name" "rem-dom",
    "column" 1,
    "line" 479,
    "source"
    "(defn rem-dom\n  ([s x dom]\n     (let [x (root-var s x)]\n       (rem-dom s x dom nil)))\n  ([s x dom seenset]\n     (let [v (root-val s x)\n           s (if (subst-val? v)\n               (let [new-doms (dissoc (:doms v) dom)]\n                 (if (and (zero? (count new-doms)) (not= (:v v) ::unbound))\n                   (update-var s x (:v v))\n                   (update-var s x (assoc v :doms new-doms))))\n               s)]\n       (sync-eset s v seenset\n         (fn [s y] (rem-dom s y dom (conj (or seenset #{}) x)))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "bindable?",
    "column" 1,
    "line" 708,
    "source"
    "(defn bindable? [x]\n  (or (lvar? x)\n    (instance? IBindable x)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic",
    "name" "a-sym",
    "column" 1,
    "line" 1760,
    "source" "(def a-sym (partial sym-helper \"a\"))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "extend-rel",
    "macro" true,
    "line" 1853,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro extend-rel [name & args]\n  (let [arity (count args)\n        r (range 1 (clojure.core/inc arity))\n        as (map a-sym r)\n        indexed (vec (filter (fn [[a i]]\n                               (-> a meta :index))\n                             (map vector\n                                  args\n                                  (range 1 (clojure.core/inc arity)))))\n        check-lvar (fn [[o i]]\n                     (let [a (a-sym i)]\n                       `((not (clojure.core.logic/contains-lvar? (clojure.core.logic/walk* ~'a ~a)))\n                         ((deref ~(index-sym name arity o)) (clojure.core.logic/walk* ~'a ~a)))))\n        indexed-set (fn [[o i]]\n                      `(def ~(index-sym name arity o) (atom {})))]\n    (if (<= arity 20)\n     `(do\n        (def ~(set-sym name arity) (atom #{}))\n        ~@(map indexed-set indexed)\n        (add-indexes ~name ~arity '~indexed)\n        (setfn ~name ~arity\n               (fn [~@as]\n                 (fn [~'a]\n                   (let [set# (cond\n                               ~@(mapcat check-lvar indexed)\n                               :else (deref ~(set-sym name arity)))]\n                     (to-stream\n                      (->> set#\n                           (map (fn [cand#]\n                                  (when-let [~'a ((== [~@as] cand#) ~'a)]\n                                    ~'a)))))))))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["name" "&" "args"]]}
   {"arglists" [[] ["oc"] ["oc" "meta"]],
    "ns" "clojure.core.logic",
    "name" "tabled-s",
    "column" 1,
    "line" 514,
    "source"
    "(defn tabled-s\n  ([] (tabled-s false))\n  ([oc] (tabled-s oc nil))\n  ([oc meta]\n     (-> (with-meta (make-s) meta)\n       (assoc :oc oc)\n       (assoc :ts (atom {})))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["cache" "ansv*" "f"]],
    "ns" "clojure.core.logic",
    "name" "make-suspended-stream",
    "column" 1,
    "line" 2008,
    "source"
    "(defn make-suspended-stream [cache ansv* f]\n  (SuspendedStream. cache ansv* f))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic",
    "name" "stopcg",
    "column" 1,
    "line" 2209,
    "source"
    "(defn stopcg [c]\n  (fn [a]\n    (assoc a :cs (runc (:cs a) c false))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic",
    "name" "empty-f",
    "column" 1,
    "line" 523,
    "source" "(def empty-f (fn []))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "!=",
    "line" 2577,
    "column" 1,
    "doc"
    "Disequality constraint. Ensures that u and v will never\n   unify. u and v can be complex terms.",
    "tag" nil,
    "source"
    "(defn !=\n  \"Disequality constraint. Ensures that u and v will never\n   unify. u and v can be complex terms.\"\n  [u v]\n  (fn [a]\n    (let [cs (disunify a u v)]\n      (if-not (nil? cs)\n        (let [p (:prefixc cs)]\n          (when-not (empty? p)\n            (if  (some (fn [[u v]] (nil? (unify a u v))) p)\n              a\n              ((cgoal (!=c p)) a))))\n        a))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["u" "v"]]}
   {"ns" "clojure.core.logic",
    "name" "matcha",
    "macro" true,
    "line" 1679,
    "column" 1,
    "doc" "Define a soft cut pattern match. See conda.",
    "tag" nil,
    "source"
    "(defmacro matcha\n  \"Define a soft cut pattern match. See conda.\"\n  [xs & cs]\n  (binding [*locals* (env-locals xs (keys &env))]\n    (handle-clauses `conda xs cs)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["xs" "&" "cs"]]}
   {"arglists" [["s" "x" "doms"]],
    "ns" "clojure.core.logic",
    "name" "merge-doms",
    "column" 1,
    "line" 543,
    "source"
    "(defn merge-doms [s x doms]\n  (let [xdoms (:doms (root-val s x))]\n    (loop [doms (seq doms) s s]\n      (if doms\n        (let [[dom domv] (first doms)]\n          (let [xdomv (get xdoms dom ::not-found)\n                ndomv (if (= xdomv ::not-found)\n                        domv\n                        (-merge-doms domv xdomv))]\n            (when ndomv\n              (recur (next doms)\n                (add-dom s x dom ndomv #{})))))\n        s))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["c" "c'" "a"]],
    "ns" "clojure.core.logic",
    "name" "entailed?",
    "column" 1,
    "line" 2216,
    "source"
    "(defn entailed? [c c' a]\n  (let [id (id c)]\n    (and (or ((-> a :cs :cm) id)\n             (nil? id))\n         (-entailed? c'))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "nonlvaro",
    "macro" true,
    "line" 1397,
    "column" 1,
    "doc"
    "A goal that succeeds if the argument is not fresh. v must be a\n  logic variable. Non-relational.",
    "tag" nil,
    "source"
    "(defmacro nonlvaro\n  \"A goal that succeeds if the argument is not fresh. v must be a\n  logic variable. Non-relational.\"\n  [v]\n  `(fn [a#]\n     (if (not (lvar? (walk a# ~v)))\n       a# nil)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["v"]]}
   {"arglists" [["x" "fs"]],
    "ns" "clojure.core.logic",
    "name" "-featurec",
    "column" 1,
    "line" 2667,
    "source"
    "(defn -featurec\n  [x fs]\n  (reify\n    IConstraintStep\n    (-step [this s]\n      (reify\n        clojure.lang.IFn\n        (invoke [_ s]\n          ((composeg\n             (== fs x)\n             (remcg this)) s))\n        IRunnable\n        (-runnable? [_]\n          (not (lvar? (walk s x))))))\n    IConstraintOp\n    (-rator [_] `featurec)\n    (-rands [_] [x])\n    IReifiableConstraint\n    (-reifyc [_ v r a]\n      (let [fs (into {} fs)\n            r  (-reify* r (walk* a fs))]\n        `(featurec ~(walk* r x) ~(walk* r fs))))\n    IConstraintWatchedStores\n    (-watched-stores [this] #{::subst})))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x" "k" "v"]],
    "ns" "clojure.core.logic",
    "name" "assoc-meta",
    "column" 1,
    "line" 18,
    "source"
    "(defn assoc-meta [x k v]\n  (with-meta x (assoc (meta x) k v)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["c" "&" "args"]],
    "ns" "clojure.core.logic",
    "name" "nafc",
    "column" 1,
    "line" 2838,
    "source" "(defn nafc [c & args]\n  (cgoal (-nafc c args)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "rembero",
    "line" 2602,
    "column" 1,
    "doc"
    "A relation between l and o where is removed from\n   l exactly one time.",
    "tag" nil,
    "source"
    "(defne rembero\n  \"A relation between l and o where is removed from\n   l exactly one time.\"\n  [x l o]\n  ([_ [x . xs] xs])\n  ([_ [y . ys] [y . zs]]\n     (!= y x)\n     (rembero x ys zs)))",
    "file" "clojure/core/logic.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic",
    "name" "pair",
    "line" 199,
    "column" 1,
    "declared" true,
    "tag" nil,
    "source"
    "(declare empty-s choice lvar lvar? pair lcons run-constraints*)",
    "file" "clojure/core/logic.clj",
    "arglists" nil}
   {"ns" "clojure.core.logic",
    "name" "retraction",
    "line" 1950,
    "column" 1,
    "doc" "Remove a fact from a relation defined with defrel.",
    "tag" nil,
    "source"
    "(defn retraction\n  \"Remove a fact from a relation defined with defrel.\"\n  [rel & tuple]\n  (retractions rel [(vec tuple)]))",
    "file" "clojure/core/logic.clj",
    "arglists" [["rel" "&" "tuple"]]}
   {"ns" "clojure.core.logic",
    "name" "map->SubstValue",
    "line" 182,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.logic.SubstValue, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord SubstValue [v doms eset]\n  Object\n  (toString [_]\n    (str v)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["m__5818__auto__"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "subst?",
    "column" 1,
    "line" 525,
    "source" "(defn subst? [x]\n  (instance? Substitutions x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "lvar?",
    "column" 1,
    "line" 702,
    "source" "(defn lvar? [x]\n  (instance? IVar x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["s" "x" "dom" "f"] ["s" "x" "dom" "f" "seenset"]],
    "ns" "clojure.core.logic",
    "name" "update-dom",
    "column" 1,
    "line" 465,
    "source"
    "(defn update-dom\n  ([s x dom f]\n     (let [x (root-var s x)]\n       (update-dom s x dom f nil)))\n  ([s x dom f seenset]\n     (let [v (root-val s x)\n           v (if (lvar? v)\n               (subst-val ::unbound)\n               v)\n           doms (:doms v)\n           s (update-var s x (assoc-dom v dom (f (get doms dom))))]\n       (sync-eset s v seenset\n         (fn [s y] (update-dom s y dom f (conj (or seenset #{}) x)))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "ex",
    "line" 1482,
    "column" 1,
    "doc"
    "Takes a list of vars to declare fresh and a term t to be unified\n   with relation argument a.",
    "tag" nil,
    "source"
    "(defn- ex\n  \"Takes a list of vars to declare fresh and a term t to be unified\n   with relation argument a.\"\n  ([vs t a]\n     `(fresh [~@vs]\n        (== ~t ~a)))\n  ([vs t a exprs]\n     (if (fresh-expr? exprs)\n       `(fresh [~@vs]\n          (== ~t ~a)\n          ~exprs)\n       `(fresh [~@vs]\n          (== ~t ~a)\n          ~@exprs))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["vs" "t" "a"] ["vs" "t" "a" "exprs"]]}
   {"arglists" [[]],
    "ns" "clojure.core.logic",
    "name" "answer-cache",
    "column" 1,
    "line" 1998,
    "source" "(defn answer-cache [] (AnswerCache. () #{} nil))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["v" "f"]],
    "ns" "clojure.core.logic",
    "name" "walk-record-term",
    "column" 1,
    "line" 945,
    "source"
    "(defn walk-record-term [v f]\n  (with-meta\n    (loop [v v r (-uninitialized v)]\n      (if (seq v)\n        (let [[vfk vfv] (first v)]\n          (recur (next v) (assoc r (walk-term (f vfk) f)\n                                 (walk-term (f vfv) f))))\n        r))\n    (meta v)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "tree-term?",
    "column" 1,
    "line" 860,
    "source"
    "(defn tree-term? [x]\n  (or (coll? x)\n      (instance? ITreeTerm x)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["v"]],
    "ns" "clojure.core.logic",
    "name" "seqc",
    "column" 1,
    "line" 2921,
    "source"
    "(defn seqc [v]\n  (fixc v\n    (fn [t _ _]\n      (cond\n        (sequential? t) succeed\n        (lcons? t) (seqc (lnext t))\n        :else fail))\n    (fn [_ v _ r a]\n      `(seqc ~(-reify a v r)))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "-run",
    "macro" true,
    "line" 1179,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro -run [opts [x :as bindings] & goals]\n  (if (> (count bindings) 1)\n    (let [[rbindings as-key [as]] (partition-by #{:as} bindings)]\n      (if (seq as-key)\n        `(-run ~opts [~as] (fresh [~@rbindings] (== ~as [~@rbindings]) ~@goals))\n        `(-run ~opts [q#] (fresh ~bindings (== q# ~bindings) ~@goals))))\n    `(let [opts# ~opts\n           xs# (take* (fn []\n                        ((fresh [~x]\n                           ~@goals\n                           (reifyg ~x))\n                         (tabled-s (:occurs-check opts#)\n                            (merge {:reify-vars true} opts#)))))]\n       (if-let [n# (:n opts#)]\n         (take n# xs#)\n         xs#))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["opts" ["x" "as" "bindings"] "&" "goals"]]}
   {"ns" "clojure.core.logic",
    "name" "def-apply-to-helper",
    "macro" true,
    "line" 1781,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro def-apply-to-helper [n]\n  (let [r (range 1 (clojure.core/inc n))\n        args (map a-sym r)\n        arg-binds (fn [n]\n                    (mapcat (fn [a]\n                              `(~a (first ~'arglist)\n                                   ~'arglist (next ~'arglist)))\n                            (take n args)))\n        case-clause (fn [n]\n                      `(~n (let [~@(arg-binds (dec n))]\n                            (.invoke ~'ifn ~@(take (dec n) args)\n                                     (clojure.lang.Util/ret1\n                                      (first ~'arglist) nil)))))]\n   `(defn ~'apply-to-helper\n      [~(with-meta 'ifn {:tag clojure.lang.IFn}) ~'arglist]\n      (case (clojure.lang.RT/boundedLength ~'arglist 20)\n            ~@(mapcat case-clause r)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["n"]]}
   {"ns" "clojure.core.logic",
    "name" "->Substitutions",
    "line" 272,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.Substitutions.",
    "tag" nil,
    "source"
    "(deftype Substitutions [s vs ts cs cq cqs oc _meta]\n  Object\n  (equals [this o]\n    (or (identical? this o)\n        (and (.. this getClass (isInstance o))\n             (= s (:s o)))))\n  ;; TODO: prn doesn't work anymore on empty-s, why not?\n  (toString [_] (str s))\n\n  clojure.lang.Counted\n  (count [this] (count s))\n\n  clojure.lang.IObj\n  (meta [this] _meta)\n  (withMeta [this new-meta]\n    (Substitutions. s vs ts cs cq cqs oc new-meta))\n\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :s   s\n      :vs  vs\n      :ts  ts\n      :cs  cs\n      :cq  cq\n      :cqs cqs\n      :oc  oc\n      not-found))\n\n  clojure.lang.IPersistentCollection\n  (cons [this [k v]]\n    (if (lvar? k)\n      (assoc this k v)\n      (throw (Exception. (str \"key must be a logic var\")))))\n  (empty [this] empty-s)\n  (equiv [this o]\n    (.equals this o))\n\n  clojure.lang.Associative\n  (containsKey [this k]\n    (contains? #{:s :vs :cs :cq :cqs :oc} k))\n  (entryAt [this k]\n    (case k\n      :s   [:s s]\n      :vs  [:vs vs]\n      :ts  [:ts ts]\n      :cs  [:cs cs]\n      :cq  [:cq cq]\n      :cqs [:cqs cqs]\n      :oc  [:oc cqs]\n      nil))\n  (assoc [this k v]\n    (case k\n      :s   (Substitutions. v vs ts cs cq cqs oc _meta)\n      :vs  (Substitutions. s  v ts cs cq cqs oc _meta)\n      :ts  (Substitutions. s vs  v cs cq cqs oc _meta)\n      :cs  (Substitutions. s vs ts  v cq cqs oc _meta)\n      :cq  (Substitutions. s vs ts cs  v cqs oc _meta)\n      :cqs (Substitutions. s vs ts cs cq   v oc _meta)\n      :oc  (Substitutions. s vs ts cs cq cqs  v _meta)\n      (throw (Exception. (str \"Substitutions has no field for key\" k)))))\n\n  ISubstitutions\n  (ext-no-check [this u v]\n    (let [u (if-not (lvar? v)\n              (assoc-meta u ::root true)\n              u)]\n      (Substitutions. (assoc s u v) (if vs (conj vs u)) ts cs cq cqs oc _meta)))\n\n  (walk [this v]\n    (if (bindable? v)\n      (loop [lv v [v vp :as me] (find s v)]\n        (cond\n          (nil? me) lv\n          \n          (not (bindable? vp))\n          (if (subst-val? vp)\n            (let [sv (:v vp)]\n              (if (= sv ::unbound)\n                (with-meta v (assoc (meta vp) ::unbound true))\n                sv))\n            vp)\n          \n          :else (recur vp (find s vp))))\n      v))\n\n  ISubstitutionsCLP\n  (root-val [this v]\n    (if (lvar? v)\n      (loop [lv v [v vp :as me] (find s v)]\n        (cond\n          (nil? me) lv\n          (not (lvar? vp)) vp\n          :else (recur vp (find s vp))))\n      v))\n\n  (root-var [this v]\n    (if (lvar? v)\n      (if (-> v meta ::root)\n        v\n        (loop [lv v [v vp :as me] (find s v)]\n          (cond\n            (nil? me) lv\n\n            (not (lvar? vp))\n            (if (subst-val? vp)\n              (with-meta v (meta vp))\n              v)\n\n            :else (recur vp (find s vp)))))\n      v))\n  \n  (ext-run-cs [this x v]\n    (let [x  (root-var this x)\n          xs (if (lvar? v)\n               [x (root-var this v)]\n               [x])\n          s  (if oc\n               (ext this x v)\n               (ext-no-check this x v))]\n      (when s\n        ((run-constraints* xs cs ::subst) s))))\n\n  (queue [this c]\n    (let [id (id c)]\n      (if-not (cqs id)\n        (-> this\n          (assoc :cq (conj (or cq []) c))\n          (assoc :cqs (conj cqs id)))\n        this)))\n\n  (update-var [this x v]\n    (assoc this :s (assoc (:s this) x v)))\n\n  IBind\n  (bind [this g]\n    (g this))\n  IMPlus\n  (mplus [this f]\n    (choice this f))\n  ITake\n  (take* [this] this))",
    "file" "clojure/core/logic.clj",
    "arglists" [["s" "vs" "ts" "cs" "cq" "cqs" "oc" "_meta"]]}
   {"ns" "clojure.core.logic",
    "name" "ifu*",
    "macro" true,
    "line" 1310,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro ifu*\n  ([])\n  ([[e & gs] & grest]\n     `(ifu ~e [~@gs]\n           ~(if (seq grest)\n              `(delay (ifu* ~@grest))\n              nil))))",
    "file" "clojure/core/logic.clj",
    "arglists" [[] [["e" "&" "gs"] "&" "grest"]]}
   {"ns" "clojure.core.logic",
    "name" "->Choice",
    "line" 1060,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.Choice.",
    "tag" nil,
    "source"
    "(deftype Choice [a f]\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :a a\n      not-found))\n  IBind\n  (bind [this g]\n    (mplus (g a) (fn [] (bind f g))))\n  IMPlus\n  (mplus [this fp]\n    (Choice. a (fn [] (mplus (fp) f))))\n  ITake\n  (take* [this]\n    (lazy-seq (cons a (lazy-seq (take* f))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a" "f"]]}
   {"ns" "clojure.core.logic",
    "name" "-fnm",
    "macro" true,
    "line" 1569,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro -fnm [fn-gen t as & cs]\n  (binding [*locals* (env-locals as (keys &env))]\n     `(~fn-gen [~@as] ~(handle-clauses t as cs))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["fn-gen" "t" "as" "&" "cs"]]}
   {"ns" "clojure.core.logic",
    "name" "*locals*",
    "line" 11,
    "column" 1,
    "tag" nil,
    "source" "(def ^{:dynamic true} *locals*)",
    "file" "clojure/core/logic.clj",
    "dynamic" true,
    "arglists" nil}
   {"ns" "clojure.core.logic",
    "name" "firsto",
    "line" 1606,
    "column" 1,
    "doc"
    "A relation where l is a collection, such that a is the first of l",
    "tag" nil,
    "source"
    "(defn firsto\n  \"A relation where l is a collection, such that a is the first of l\"\n  [l a]\n  (fresh [d]\n    (conso a d l)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["l" "a"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "waiting-stream?",
    "column" 1,
    "line" 2014,
    "source" "(defn waiting-stream? [x]\n  (vector? x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "handle-clauses",
    "line" 1534,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- handle-clauses [t as cs]\n  `(~t\n    ~@(doall (map (handle-clause as) cs))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["t" "as" "cs"]]}
   {"ns" "clojure.core.logic",
    "name" "fact",
    "line" 1906,
    "column" 1,
    "doc" "Add a fact to a relation defined with defrel.",
    "tag" nil,
    "source"
    "(defn fact\n  \"Add a fact to a relation defined with defrel.\"\n  [rel & tuple]\n  (facts rel [(vec tuple)]))",
    "file" "clojure/core/logic.clj",
    "arglists" [["rel" "&" "tuple"]]}
   {"arglists" [["s" "v"]],
    "ns" "clojure.core.logic",
    "name" "walk*",
    "column" 1,
    "line" 212,
    "source"
    "(defn walk* [s v]\n  (let [v (walk s v)]\n    (walk-term v\n      (fn [x]\n        (let [x (walk s x)]\n         (if (tree-term? x)\n           (walk* s x)\n           x))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "retractions",
    "line" 1929,
    "column" 1,
    "doc"
    "Retract a series of facts. Takes a vector of vectors where each vector\n   represents a fact tuple, all with the same number of elements. It is not\n   an error to retract a fact that isn't true.",
    "tag" nil,
    "source"
    "(defn retractions\n  \"Retract a series of facts. Takes a vector of vectors where each vector\n   represents a fact tuple, all with the same number of elements. It is not\n   an error to retract a fact that isn't true.\"\n  ([rel [f :as tuples]]\n     (when f (retractions rel (count f) tuples)))\n  ([^Rel rel arity tuples]\n     (let [rel-ns (:ns (meta rel))\n           rel-set (var-get (ns-resolve rel-ns (set-sym (.name rel) arity)))\n           tuples (map vec tuples)]\n       (swap! rel-set (fn [s] (reduce disj s tuples)))\n       (let [indexes (indexes-for rel arity)]\n         (doseq [[o i] indexes]\n           (let [index (var-get (ns-resolve rel-ns (index-sym (.name rel) arity o)))]\n             (let [indexed-tuples (map (fn [t]\n                                         {(nth t (dec i)) #{t}})\n                                       tuples)]\n               (swap! index\n                      (fn [i]\n                        (apply difference-with set/difference i indexed-tuples))))))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["rel" ["f" "as" "tuples"]] ["rel" "arity" "tuples"]]}
   {"ns" "clojure.core.logic",
    "name" "tabled",
    "macro" true,
    "line" 2151,
    "column" 1,
    "doc"
    "Macro for defining a tabled goal. Prefer ^:tabled with the \n  defne/a/u forms over using this directly.",
    "tag" nil,
    "source"
    "(defmacro tabled\n  \"Macro for defining a tabled goal. Prefer ^:tabled with the \n  defne/a/u forms over using this directly.\"\n  [args & grest]\n  (let [uuid (symbol (str \"tabled-\" (UUID/randomUUID)))]\n    `(fn ~uuid [~@args]\n       (let [argv# ~args]\n         (fn [a#]\n           (let [key#    (-reify a# argv#)\n                 tables# (:ts a#)\n                 tables# (if-not (contains? @tables# ~uuid)\n                           (swap! tables#\n                             (fn [tables#]\n                               (if (contains? tables# ~uuid)\n                                 tables#\n                                 (assoc tables# ~uuid (atom {})))))\n                           @tables#)\n                 table#  (get tables# ~uuid)]\n             (if-not (contains? @table# key#)\n               (let [table# (swap! table#\n                              (fn [table#]\n                                (if (contains? table# key#)\n                                  table#\n                                  (assoc table# key# (atom (answer-cache))))))\n                     cache# (get table# key#)]\n                 ((fresh []\n                    ~@grest\n                    (master argv# cache#)) a#))\n               (let [cache# (get @table# key#)]\n                 (reuse a# argv# cache# nil nil)))))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["args" "&" "grest"]]}
   {"ns" "clojure.core.logic",
    "name" "->LVar",
    "line" 606,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.LVar.",
    "tag" nil,
    "source"
    "(deftype LVar [name oname hash meta]\n  IVar\n  clojure.lang.ILookup\n  (valAt [this k]\n    (.valAt this k nil))\n  (valAt [this k not-found]\n    (case k\n      :name name\n      :oname oname\n      not-found))\n\n  clojure.lang.IObj\n  (meta [this]\n    meta)\n  (withMeta [this new-meta]\n    (LVar. name oname hash new-meta))\n\n  Object\n  (toString [_] (str \"<lvar:\" name \">\"))\n\n  (equals [this o]\n    (and (instance? IVar o)\n      (identical? name (:name o))))\n\n  (hashCode [_] hash)\n\n  IUnifyTerms\n  (unify-terms [u v s]\n    (cond\n      (lvar? v)\n      (let [repoint (cond\n                      (-> u clojure.core/meta ::unbound) [u v]\n                      (-> v clojure.core/meta ::unbound) [v u]\n                      :else nil)]\n        (if repoint\n          (let [[root other] repoint\n                s (assoc s :cs (migrate (:cs s) other root))\n                s (if (-> other clojure.core/meta ::unbound)\n                    (merge-with-root s other root)\n                    s)]\n            (when s\n              (ext-no-check s other root)))\n          (ext-no-check s u v)))\n\n      (non-storable? v)\n      (throw (Exception. (str v \" is non-storable\")))\n\n      (not= v ::not-found)\n      (if (tree-term? v)\n        (ext s u v)\n        (if (-> u clojure.core/meta ::unbound)\n          (ext-no-check s u (assoc (root-val s u) :v v))\n          (ext-no-check s u v)))\n      \n      :else nil))\n\n  IReifyTerm\n  (reify-term [v s]\n    (let [rf (-> s clojure.core/meta :reify-vars)]\n      (if (fn? rf)\n        (rf v s)\n        (if rf\n          (ext s v (reify-lvar-name s))\n          (ext s v (:oname v))))))\n\n  IWalkTerm\n  (walk-term [v f] (f v))\n\n  IOccursCheckTerm\n  (occurs-check-term [v x s] (= (walk s v) x))\n\n  IBuildTerm\n  (build-term [u s]\n    (let [m (:s s)\n          cs (:cs s)\n          lv (lvar 'ignore) ]\n      (if (contains? m u)\n        s\n        (make-s (assoc m u lv) cs)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["name" "oname" "hash" "meta"]]}
   {"ns" "clojure.core.logic",
    "name" "difference-with",
    "line" 1911,
    "column" 1,
    "doc"
    "Returns a map that consists of the first map with the rest of the maps\n   removed from it. When a key is found in the first map and a later map,\n   the value from the later map will be combined with the value in the first\n   map by calling (f val-in-first val-in-later). If this function returns nil\n   then the key will be removed completely.",
    "tag" nil,
    "source"
    "(defn difference-with\n  \"Returns a map that consists of the first map with the rest of the maps\n   removed from it. When a key is found in the first map and a later map,\n   the value from the later map will be combined with the value in the first\n   map by calling (f val-in-first val-in-later). If this function returns nil\n   then the key will be removed completely.\"\n  [f & maps]\n  (when (some identity maps)\n    (let [empty-is-nil (fn [s] (if (empty? s) nil s))\n          merge-entry (fn [m [k v]]\n                         (if (contains? m k)\n                           (if-let [nv (empty-is-nil (f (get m k) v))]\n                             (assoc m k nv)\n                             (dissoc m k))\n                           m))\n          merge-map (fn [m1 m2] (reduce merge-entry (or m1 {}) (seq m2)))]\n      (reduce merge-map maps))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["f" "&" "maps"]]}
   {"arglists" [["u" "v" "s"]],
    "ns" "clojure.core.logic",
    "name" "unify-with-map*",
    "column" 1,
    "line" 887,
    "source"
    "(defn unify-with-map* [u v s]\n  (when (= (count u) (count v))\n    (loop [ks (keys u) s s]\n      (if (seq ks)\n        (let [kf (first ks)\n              vf (get v kf ::not-found)]\n          (when-not (= vf ::not-found)\n            (if-let [s (unify s (get u kf) vf)]\n              (recur (next ks) s)\n              nil)))\n        s))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "trace-s",
    "macro" true,
    "line" 1237,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro trace-s []\n  \"Goal that prints the current substitution\"\n  `(fn [a#]\n     (println (str a#))\n     a#))",
    "file" "clojure/core/logic.clj",
    "arglists" [[]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "ex*",
    "line" 1497,
    "column" 1,
    "doc"
    "Takes a sequence of pattern/argument pairs, goal expressions and\n   a set of seen variables. Returns source code that represents the\n   equivalent miniKanren series of unifications.",
    "tag" nil,
    "source"
    "(defn- ex*\n  \"Takes a sequence of pattern/argument pairs, goal expressions and\n   a set of seen variables. Returns source code that represents the\n   equivalent miniKanren series of unifications.\"\n  [[[p a :as pa] & par] exprs seen]\n  (let [vars (atom #{})\n        t    (p->term p vars)\n        vs   (set/difference @vars seen)\n        seen (reduce conj seen vs)]\n    (cond\n     (nil? pa) exprs\n     (= p '_) (ex* par exprs seen)\n     (empty? par) (if exprs\n                    (ex vs t a exprs)\n                    (ex vs t a))\n     :else (let [r (ex* par exprs seen)]\n             (if r\n               (ex vs t a r)\n               (ex vs t a))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [[[["p" "a" "as" "pa"] "&" "par"] "exprs" "seen"]]}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic",
    "name" "updatecg",
    "column" 1,
    "line" 2197,
    "source"
    "(defn updatecg [c]\n  (fn [a]\n    (assoc a :cs (updatec (:cs a) a c))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "subst-val?",
    "column" 1,
    "line" 187,
    "source" "(defn subst-val? [x]\n  (instance? SubstValue x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic",
    "name" "unbound-names",
    "column" 1,
    "line" 234,
    "source"
    "(def unbound-names\n  (let [r (range 100)]\n    (zipmap r (map (comp symbol str) (repeat \"_\") r))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["a" "x"]],
    "ns" "clojure.core.logic",
    "name" "get-dom-fd",
    "column" 1,
    "line" 2357,
    "source"
    "(defn get-dom-fd\n  [a x]\n  (if (lvar? x)\n    (get-dom a x ::fd)\n    x))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "recover-vars-from-term",
    "column" 1,
    "line" 2508,
    "source"
    "(defn recover-vars-from-term [x]\n  (let [r (atom #{})]\n    (walk-term x\n      (fn [x]\n        (if (lvar? x)\n          (do (swap! r conj x) x)\n          x)))\n    @r))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x" "f" "reifier"] ["x" "f" "runnable" "reifier"]],
    "ns" "clojure.core.logic",
    "name" "fixc",
    "column" 1,
    "line" 2905,
    "source"
    "(defn fixc\n  ([x f reifier] (fixc x f nil reifier))\n  ([x f runnable reifier]\n     (cgoal (-fixc x f runnable reifier))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "make-s",
    "line" 509,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- make-s\n  ([] (make-s {}))\n  ([m] (make-s m (make-cs)))\n  ([m cs] (Substitutions. m nil nil cs nil #{} true nil)))",
    "file" "clojure/core/logic.clj",
    "arglists" [[] ["m"] ["m" "cs"]]}
   {"arglists" [["s" "u"]],
    "ns" "clojure.core.logic",
    "name" "build",
    "column" 1,
    "line" 256,
    "source" "(defn build [s u]\n  (build-term u s))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "map->SuspendedStream",
    "line" 2003,
    "column" 1,
    "doc"
    "Factory function for class clojure.core.logic.SuspendedStream, taking a map of keywords to field values.",
    "tag" nil,
    "source"
    "(defrecord SuspendedStream [cache ansv* f]\n  ISuspendedStream\n  (ready? [this]\n    (not (identical? (:ansl @cache) ansv*))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["m__5818__auto__"]]}
   {"ns" "clojure.core.logic",
    "name" "conde",
    "macro" true,
    "line" 1151,
    "column" 1,
    "doc"
    "Logical disjunction of the clauses. The first goal in\n  a clause is considered the head of that clause. Interleaves the\n  execution of the clauses.",
    "tag" nil,
    "source"
    "(defmacro conde\n  \"Logical disjunction of the clauses. The first goal in\n  a clause is considered the head of that clause. Interleaves the\n  execution of the clauses.\"\n  [& clauses]\n  (let [a (gensym \"a\")]\n    `(fn [~a]\n       (-inc\n        (mplus* ~@(bind-conde-clauses a clauses))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "clauses"]]}
   {"ns" "clojure.core.logic",
    "name" "mplus*",
    "macro" true,
    "line" 1048,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro mplus*\n  ([e] e)\n  ([e & e-rest]\n     `(mplus ~e (fn [] (mplus* ~@e-rest)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["e"] ["e" "&" "e-rest"]]}
   {"arglists" [["x" "p"] ["x" "p" "pform"]],
    "ns" "clojure.core.logic",
    "name" "-predc",
    "column" 1,
    "line" 2772,
    "source"
    "(defn -predc\n  ([x p] (-predc x p p))\n  ([x p pform]\n     (reify\n       IConstraintStep\n       (-step [this s]\n         (reify\n           clojure.lang.IFn\n           (invoke [_ s]\n             (let [x (walk s x)]\n               (when (p x)\n                 ((remcg this) s))))\n           IRunnable\n           (-runnable? [_]\n             (not (lvar? (walk s x))))))\n       IConstraintOp\n       (-rator [_] (if (seq? pform)\n                    `(predc ~pform)\n                    `predc))\n       (-rands [_] [x])\n       IReifiableConstraint\n       (-reifyc [c v r s]\n         (if (and (not= p pform) (fn? pform))\n           (pform c v r s)\n           pform))\n       IConstraintWatchedStores\n       (-watched-stores [this] #{::subst}))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "project-binding",
    "line" 1261,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- project-binding [s]\n  (fn [var]\n    `(~var (walk* ~s ~var))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["s"]]}
   {"arglists" [["f"]],
    "ns" "clojure.core.logic",
    "name" "tramp",
    "column" 1,
    "line" 2808,
    "source"
    "(defn tramp [f]\n  (loop [f f]\n    (if (fn? f)\n      (recur (f))\n      f)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "warn",
    "line" 1408,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- warn [& msg]\n  (binding [*out* *err*]\n    (apply println \"WARNING:\" msg)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "msg"]]}
   {"arglists" [[] ["name"] ["name" "gensym"]],
    "ns" "clojure.core.logic",
    "name" "lvar",
    "column" 1,
    "line" 686,
    "source"
    "(defn lvar\n  ([]\n     (let [name (str (. clojure.lang.RT (nextID)))]\n       (LVar. name nil (.hashCode name) nil)))\n  ([name]\n     (lvar name true))\n  ([name gensym]\n     (let [oname name\n           name (if gensym\n                  (str name \"__\" (. clojure.lang.RT (nextID)))\n                  (str name))]\n       (LVar. name oname (.hashCode name) nil))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "umi",
    "macro" true,
    "line" 717,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro umi\n  [& args]\n  (if (resolve 'unchecked-multiply-int)\n    `(unchecked-multiply-int ~@args)\n    `(unchecked-multiply ~@args)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "args"]]}
   {"ns" "clojure.core.logic",
    "name" "fne",
    "macro" true,
    "line" 1634,
    "column" 1,
    "doc"
    "Define an anonymous goal fn. Supports pattern matching. All\n   patterns will be tried. See conde.",
    "tag" nil,
    "source"
    "(defmacro fne\n  \"Define an anonymous goal fn. Supports pattern matching. All\n   patterns will be tried. See conde.\"\n  [& rest]\n  `(fnm conde ~@rest))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "rest"]]}
   {"ns" "clojure.core.logic",
    "name" "->PMap",
    "line" 2636,
    "column" 1,
    "doc"
    "Positional factory function for class clojure.core.logic.PMap.",
    "tag" nil,
    "source"
    "(defrecord PMap []\n  INonStorable\n\n  IUnifyTerms\n  (unify-terms [u v s]\n    (if (map? v)\n      (unify-with-pmap* u v s)\n      nil))\n\n  IUnifyWithRecord\n  (unify-with-record [u v s]\n    (if (map? v)\n      (unify-with-pmap* u v s)\n      nil))\n\n  IUninitialized\n  (-uninitialized [_] (PMap.)))",
    "file" "clojure/core/logic.clj",
    "arglists" [[]]}
   {"ns" "clojure.core.logic",
    "name" "facts",
    "line" 1887,
    "column" 1,
    "doc"
    "Define a series of facts. Takes a vector of vectors where each vector\n   represents a fact tuple, all with the same number of elements.",
    "tag" nil,
    "source"
    "(defn facts\n  \"Define a series of facts. Takes a vector of vectors where each vector\n   represents a fact tuple, all with the same number of elements.\"\n  ([rel [f :as tuples]] (facts rel (count f) tuples))\n  ([^Rel rel arity tuples]\n     (let [rel-ns (:ns (meta rel))\n           rel-set (var-get (ns-resolve rel-ns (set-sym (.name rel) arity)))\n           tuples (map vec tuples)]\n       (swap! rel-set (fn [s] (into s tuples)))\n       (let [indexes (indexes-for rel arity)]\n         (doseq [[o i] indexes]\n           (let [index (var-get (ns-resolve rel-ns (index-sym (.name rel) arity o)))]\n             (let [indexed-tuples (map (fn [t]\n                                         {(nth t (dec i)) #{t}})\n                                       tuples)]\n               (swap! index\n                      (fn [i]\n                        (apply merge-with set/union i indexed-tuples))))))))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["rel" ["f" "as" "tuples"]] ["rel" "arity" "tuples"]]}
   {"ns" "clojure.core.logic",
    "name" "matchu",
    "macro" true,
    "line" 1685,
    "column" 1,
    "doc" "Define a committed choice goal. See condu.",
    "tag" nil,
    "source"
    "(defmacro matchu\n  \"Define a committed choice goal. See condu.\"\n  [xs & cs]\n  (binding [*locals* (env-locals xs (keys &env))]\n    (handle-clauses `condu xs cs)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["xs" "&" "cs"]]}
   {"ns" "clojure.core.logic",
    "name" "lcons",
    "line" 844,
    "column" 1,
    "doc"
    "Constructs a sequence a with an improper tail d if d is a logic variable.",
    "tag" nil,
    "source"
    "(defn lcons\n  \"Constructs a sequence a with an improper tail d if d is a logic variable.\"\n  [a d]\n  (if (or (coll? d) (nil? d))\n    (cons a (seq d))\n    (LCons. a d -1 nil)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a" "d"]]}
   {"ns" "clojure.core.logic",
    "name" "RelHelper",
    "macro" true,
    "line" 1803,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro RelHelper [arity]\n  (let [r (range 1 (+ arity 2))\n        fs (map f-sym r)\n        mfs (map #(with-meta % {:volatile-mutable true :tag clojure.lang.IFn})\n                 fs)\n        create-sig (fn [n]\n                     (let [args (map a-sym (range 1 (clojure.core/inc n)))]\n                       `(invoke [~'_ ~@args]\n                                  (~(f-sym n) ~@args))))\n        set-case (fn [[f arity]]\n                   `(~arity (set! ~f ~'f)))]\n    `(do\n       (deftype ~'Rel [~'name ~'indexes ~'meta\n                       ~@mfs]\n         clojure.lang.IObj\n         (~'withMeta [~'_ ~'meta]\n           (~'Rel. ~'name ~'indexes ~'meta ~@fs))\n         (~'meta [~'_]\n           ~'meta)\n         clojure.lang.IFn\n         ~@(map create-sig r)\n         (~'applyTo [~'this ~'arglist]\n            (~'apply-to-helper ~'this ~'arglist))\n         ~'IRel\n         (~'setfn [~'_ ~'arity ~'f]\n           (case ~'arity\n                 ~@(mapcat set-case (map vector fs r))))\n         (~'indexes-for [~'_ ~'arity]\n           ((deref ~'indexes) ~'arity))\n         (~'add-indexes [~'_ ~'arity ~'index]\n           (swap! ~'indexes assoc ~'arity ~'index)))\n       (defmacro ~'defrel \n         \"Define a relation for adding facts. Takes a name and some fields.\n         Use fact/facts to add facts and invoke the relation to query it.\"\n         [~'name ~'& ~'rest]\n         (defrel-helper ~'name ~arity ~'rest)))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["arity"]]}
   {"arglists" [["s" "x" "attr" "attrv"]],
    "ns" "clojure.core.logic",
    "name" "add-attr",
    "column" 1,
    "line" 417,
    "source"
    "(defn add-attr [s x attr attrv]\n  (let [x (root-var s x)\n        v (root-val s x)]\n    (if (subst-val? v)\n      (update-var s x (assoc-meta v attr attrv))\n      (let [v (if (lvar? v) ::unbound v)]\n        (ext-no-check s x (with-meta (subst-val v) {attr attrv}))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "fail",
    "line" 1120,
    "column" 1,
    "doc" "A goal that always fails.",
    "tag" nil,
    "source" "(defn fail\n  \"A goal that always fails.\"\n  [a] nil)",
    "file" "clojure/core/logic.clj",
    "arglists" [["a"]]}
   {"private" true,
    "ns" "clojure.core.logic",
    "name" "cond-clauses",
    "line" 1354,
    "column" 1,
    "tag" nil,
    "source"
    "(defn- cond-clauses [a]\n  (fn [goals]\n    `((~(first goals) ~a) ~@(rest goals))))",
    "file" "clojure/core/logic.clj",
    "arglists" [["a"]]}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "force-ans",
    "column" 1,
    "line" 2425,
    "source"
    "(defn force-ans [x]\n  (fn [a]\n    ((let [v (walk a x)]\n       (if (lvar? v)\n         (-force-ans (get-dom-fd a x) v)\n         (let [x (root-var a x)]\n           (if (sequential? v)\n             (-force-ans (sort-by-strategy v x a) x)\n             (-force-ans v x))))) a)))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["c"]],
    "ns" "clojure.core.logic",
    "name" "ientailed?",
    "column" 1,
    "line" 2213,
    "source"
    "(defn ientailed? [c]\n  (instance? clojure.core.logic.protocols.IEntailed c))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x"]],
    "ns" "clojure.core.logic",
    "name" "reifyg",
    "column" 1,
    "line" 2327,
    "source"
    "(defn reifyg [x]\n  (all\n   (enforce-constraints x)\n   (fn [a]\n     (let [v (walk* a x)\n           r (-reify* (with-meta empty-s (meta a)) v)]\n       (if (zero? (count r))\n         (choice v empty-f)\n         (let [v (walk* r v)]\n           (reify-constraints v r a)))))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"ns" "clojure.core.logic",
    "name" "defnm",
    "macro" true,
    "line" 1580,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro defnm [t n & rest]\n  (let [[n [as & cs]] (name-with-attributes n rest)\n        e (if (-> n meta :tabled)\n            `(fnm ~t ~as :tabled ~@cs)\n            `(fnm ~t ~as ~@cs))]\n    `(def ~n ~e)))",
    "file" "clojure/core/logic.clj",
    "arglists" [["t" "n" "&" "rest"]]}
   {"ns" "clojure.core.logic",
    "name" "run-nc*",
    "macro" true,
    "line" 1212,
    "column" 1,
    "doc"
    "Executes goals until results are exhausted. Does not occurs-check.",
    "tag" nil,
    "source"
    "(defmacro run-nc*\n  \"Executes goals until results are exhausted. Does not occurs-check.\"\n  [& goals]\n  `(run-nc false ~@goals))",
    "file" "clojure/core/logic.clj",
    "arglists" [["&" "goals"]]}
   {"arglists" [["v" "r" "a"]],
    "ns" "clojure.core.logic",
    "name" "reify-constraints",
    "column" 1,
    "line" 2316,
    "source"
    "(defn reify-constraints [v r a]\n  (let [cs  (:cs  a)\n        rcs (->> (vals (:cm cs))\n                 (filter reifiable?)\n                 (map #(-reifyc % v r a))\n                 (filter #(not (nil? %)))\n                 (into #{}))]\n    (if (empty? rcs)\n      (choice v empty-f)\n      (choice `(~v :- ~@rcs) empty-f))))",
    "file" "clojure/core/logic.clj",
    "tag" nil}
   {"arglists" [["x" "fc" "reifier"]],
    "ns" "clojure.core.logic",
    "name" "treec",
    "column" 1,
    "line" 2910,
    "source"
    "(defn treec [x fc reifier]\n  (fixc x\n    (fn loop [t a reifier]\n      (if (tree-term? t)\n        (composeg*\n          (fc t)\n          (constrain-tree t\n            (fn [t a] ((fixc t loop reifier) a))))\n        (fc t)))\n    reifier))",
    "file" "clojure/core/logic.clj",
    "tag" nil}],
  "clojure.core.logic.bench"
  [{"arglists" [["rows" "x" "y"]],
    "ns" "clojure.core.logic.bench",
    "name" "get-square",
    "column" 1,
    "line" 600,
    "source"
    "(defn get-square [rows x y]\n  (for [x (range x (+ x 3))\n        y (range y (+ y 3))]\n    (get-in rows [x y])))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.logic.bench",
    "name" "small-sudokufd",
    "column" 1,
    "line" 555,
    "source"
    "(defn small-sudokufd []\n  (run-nc 1 [q]\n    (fresh [a1 a2 a3 a4\n            b1 b2 b3 b4\n            c1 c2 c3 c4\n            d1 d2 d3 d4]\n      (== q [[a1 a2 a3 a4]\n             [b1 b2 b3 b4]\n             [c1 c2 c3 c4]\n             [d1 d2 d3 d4]])\n      (fd/in a1 a2 a3 a4\n            b1 b2 b3 b4\n            c1 c2 c3 c4\n            d1 d2 d3 d4\n            (fd/domain 1 2 3 4))\n      (let [row1 [a1 a2 a3 a4]\n            row2 [b1 b2 b3 b4]\n            row3 [c1 c2 c3 c4]\n            row4 [d1 d2 d3 d4]\n            col1 [a1 b1 c1 d1]\n            col2 [a2 b2 c2 d2]\n            col3 [a3 b3 c3 d3]\n            col4 [a4 b4 c4 d4]\n            sq1 [a1 a2 b1 b2]\n            sq2 [a3 a4 b3 b4]\n            sq3 [c1 c2 d1 d2]\n            sq4 [c3 c4 d3 d4]]\n        (everyg fd/distinct\n           [row1 row2 row3 row4\n            col1 col2 col3 col4\n            sq1 sq2 sq3 sq4])))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "nrevo",
    "column" 1,
    "line" 41,
    "source"
    "(defne nrevo [l o]\n  ([() ()])\n  ([[a . d] _]\n     (fresh [r]\n       (nrevo d r)\n       (appendo r [a] o))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "subchecko",
    "column" 1,
    "line" 491,
    "source"
    "(defne subchecko [w sl r o n]\n  ;; we have no more stones to test in sl to test w with\n  ([_ () _ _ _]\n     (fresh [hr]\n       (fd/in hr (fd/interval 1 n))\n       (matche [r o]\n         ;; r is not empty, we add w to the output only if\n         ;; w is head of r + 1\n         ([[hr . _] [w . r]] (fd/+ hr 1 w))\n         ;; r is empty, just add the weight\n         ;; only works for w == 1\n         ([() [w . r]]))))\n  ;; we have stones to in sl to test w with\n  ([_ [hsl . rsl] _ _ _]\n     (fresh [w-hsl w+hsl o0 o1 nw]\n       (fd/in hsl w-hsl w+hsl (fd/interval 1 n))\n       (fd/+ hsl w-hsl w) (fd/+ hsl w w+hsl)\n       ;; attempt to construct values prior w\n       (subchecko w-hsl rsl r  o0 n)\n       ;; attempt to construct values around w\n       (subchecko w     rsl o0 o1 n)\n       ;; attempt to construct values after w\n       (subchecko w+hsl rsl o1 o  n))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.logic.bench",
    "name" "simplefd",
    "column" 1,
    "line" 445,
    "source"
    "(defn simplefd []\n  (run* [x y]\n    (fd/in x y (fd/interval 0 9))\n    (fd/+ x y 9)\n    (fresh [p0 p1]\n      (fd/* 2 x p0)\n      (fd/* 4 y p1)\n      (fd/+ p0 p1 24))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["vars"]],
    "ns" "clojure.core.logic.bench",
    "name" "print-solution",
    "column" 1,
    "line" 653,
    "source"
    "(defn print-solution [vars]\n  (doseq [row-group (->> vars\n                        (partition 9)\n                        (partition 3)\n                        (interpose \"\\n\\n\"))]\n    (if-not (string? row-group)\n      (doseq [row (interpose \"\\n\" row-group)]\n        (if-not (string? row)\n          (doseq [x (->> row\n                         (partition 3)\n                         (map #(interpose \" \" %))\n                         (interpose \"  \"))]\n            (print (apply str x)))\n          (print row)))\n      (print row-group)))\n  (println) (println))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["x" "y"]],
    "ns" "clojure.core.logic.bench",
    "name" "not-adjacento",
    "column" 1,
    "line" 405,
    "source"
    "(defn not-adjacento [x y]\n  (fresh [f]\n    (fd/in f (fd/interval 1 5))\n    (conde\n      [(fd/+ x f y) (fd/< 1 f)]\n      [(fd/+ y f x) (fd/< 1 f)])))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["rows"]],
    "ns" "clojure.core.logic.bench",
    "name" "->cols",
    "column" 1,
    "line" 618,
    "source" "(defn ->cols [rows]\n  (apply map vector rows))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["rows"]],
    "ns" "clojure.core.logic.bench",
    "name" "->squares",
    "column" 1,
    "line" 621,
    "source"
    "(defn ->squares [rows]\n  (for [x (range 0 9 3)\n        y (range 0 9 3)]\n    (get-square rows x y)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["ls" "res"]],
    "ns" "clojure.core.logic.bench",
    "name" "magic-sum",
    "column" 1,
    "line" 886,
    "source"
    "(defn magic-sum [ls res]\n  (conde\n    [(== ls []) (== res 0)]\n    [(== ls [res])]\n    [(fresh [h t inter]\n       (conso h t ls)\n       (fd/+ h inter res)\n       (magic-sum t inter))]))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "qsorto",
    "column" 1,
    "line" 386,
    "source"
    "(defne qsorto [l r r0]\n  ([[] _ r])\n  ([[x . lr] _ _]\n     (fresh [l1 l2 r1]\n       (partitiono lr x l1 l2)\n       (qsorto l2 r1 r0)\n       (qsorto l1 r (lcons x r1)))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "connected_2-y-index",
    "column" 1,
    "line" 114,
    "source" "(defrel connected ^:index x ^:index y)",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.logic.bench",
    "name" "simple-fd-eq",
    "column" 1,
    "line" 456,
    "source"
    "(defn simple-fd-eq []\n  (run* [x y]\n    (fd/in x y (fd/interval 0 9))\n    (fd/eq\n     (= (+ x y) 9)\n     (= (+ (* x 2) (* y 4)) 24))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["n"]],
    "ns" "clojure.core.logic.bench",
    "name" "magic",
    "column" 1,
    "line" 895,
    "source" nil,
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["hs"]],
    "ns" "clojure.core.logic.bench",
    "name" "zebrao",
    "column" 1,
    "line" 77,
    "source"
    "(defn zebrao [hs]\n  (all\n   (== [(lvar) (lvar) [(lvar) (lvar) 'milk (lvar) (lvar)] (lvar) (lvar)] hs)\n   (firsto hs ['norwegian (lvar) (lvar) (lvar) (lvar)])\n   (nexto ['norwegian (lvar) (lvar) (lvar) (lvar)] [(lvar) (lvar) (lvar) (lvar) 'blue] hs)\n   (righto [(lvar) (lvar) (lvar) (lvar) 'ivory] [(lvar) (lvar) (lvar) (lvar) 'green] hs)\n   (membero ['englishman (lvar) (lvar) (lvar) 'red] hs)\n   (membero [(lvar) 'kools (lvar) (lvar) 'yellow] hs)\n   (membero ['spaniard (lvar) (lvar) 'dog (lvar)] hs)\n   (membero [(lvar) (lvar) 'coffee (lvar) 'green] hs)\n   (membero ['ukrainian (lvar) 'tea (lvar) (lvar)] hs)\n   (membero [(lvar) 'lucky-strikes 'oj (lvar) (lvar)] hs)\n   (membero ['japanese 'parliaments (lvar) (lvar) (lvar)] hs)\n   (membero [(lvar) 'oldgolds (lvar) 'snails (lvar)] hs)\n   (nexto [(lvar) (lvar) (lvar) 'horse (lvar)] [(lvar) 'kools (lvar) (lvar) (lvar)] hs)\n   (nexto [(lvar) (lvar) (lvar) 'fox (lvar)] [(lvar) 'chesterfields (lvar) (lvar) (lvar)] hs)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [[["fa" "_"] ["fb" "_"]]],
    "ns" "clojure.core.logic.bench",
    "name" "sort-dwellers",
    "column" 1,
    "line" 422,
    "source"
    "(defn sort-dwellers [[fa _] [fb _]]\n  (cond (< fa fb) -1 (= fa fb) 0 :else 1))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["ns"]],
    "ns" "clojure.core.logic.bench",
    "name" "->answer",
    "column" 1,
    "line" 425,
    "source"
    "(defn ->answer [ns]\n  (->> (map vector ns [:baker :cooper :fletcher :miller :smith])\n       (sort sort-dwellers)\n       (map second)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["hints"]],
    "ns" "clojure.core.logic.bench",
    "name" "sudokufd",
    "column" 1,
    "line" 626,
    "source"
    "(defn sudokufd [hints]\n  (let [vars (repeatedly 81 lvar) \n        rows (->rows vars)\n        cols (->cols rows)\n        sqs  (->squares rows)]\n    (run-nc 1 [q]\n      (== q vars)\n      ;;(distribute q ::l/ff)\n      (everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) vars)\n      (init vars hints)\n      (everyg fd/distinct rows)\n      (everyg fd/distinct cols)\n      (everyg fd/distinct sqs))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "connected_2-set",
    "column" 1,
    "line" 114,
    "source" "(defrel connected ^:index x ^:index y)",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["x" "y" "l"]],
    "ns" "clojure.core.logic.bench",
    "name" "nexto",
    "column" 1,
    "line" 72,
    "source"
    "(defn nexto [x y l]\n  (conde\n    [(righto x y l)]\n    [(righto y x l)]))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.bench",
    "name" "all-connected-to-allo",
    "line" 131,
    "column" 1,
    "doc"
    "Collect all cliques in l. l must be bounded to ensure\n   termination.",
    "tag" nil,
    "source"
    "(defne all-connected-to-allo\n  \"Collect all cliques in l. l must be bounded to ensure\n   termination.\"\n  [l]\n  ([()])\n  ([[h . t]]\n     (connected-to-allo h t)\n     (all-connected-to-allo t)))",
    "file" "clojure/core/logic/bench.clj",
    "arglists" nil}
   {"arglists" [["send" "more" "money"]],
    "ns" "clojure.core.logic.bench",
    "name" "send-money-quicklyo",
    "column" 1,
    "line" 297,
    "source"
    "(defn send-money-quicklyo [send more money]\n  (fresh [l]\n    (do-send-moolao [send more money] (range 10) l)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "do-send-moolao",
    "column" 1,
    "line" 278,
    "source"
    "(defne do-send-moolao [q l ll]\n  ([[send more money] _ _]\n     (fresh [s e n d m o r y\n             l1 l2 l3 l4 l5 l6 l7 l8 l9]\n       (first-digito s l l1)\n       (first-digito m l1 l2)\n       (digito e l2 l3)\n       (digito n l3 l4)\n       (digito d l4 l5)\n       (digito o l5 l6)\n       (digito r l6 l7)\n       (digito y l7 l8)\n       (project [s e n d m o r y]\n         (== send (+ (* s 1000) (* e 100) (* n 10) d))\n         (== more (+ (* m 1000) (* o 100) (* r 10) e))\n         (== money (+ (* m 10000) (* o 1000) (* n 100) (* e 10) y))\n         (project [send more]\n           (== money (+ send more)))))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["vars" "hints"]],
    "ns" "clojure.core.logic.bench",
    "name" "init",
    "column" 1,
    "line" 605,
    "source"
    "(defn init [vars hints]\n  (if (seq vars)\n    (let [hint (first hints)]\n      (all\n       (if-not (zero? hint)\n         (== (first vars) hint)\n         succeed)\n       (init (next vars) (next hints))))\n    succeed))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["n" "grid"]],
    "ns" "clojure.core.logic.bench",
    "name" "magic-cols",
    "column" 1,
    "line" 875,
    "source"
    "(defn magic-cols [n grid]\n  (apply map list (partition n grid)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["vars"]],
    "ns" "clojure.core.logic.bench",
    "name" "verify",
    "column" 1,
    "line" 642,
    "source"
    "(defn verify [vars]\n  (let [rows (->rows vars)\n        cols (->cols rows)\n        sqs  (->squares rows)\n        verify-group (fn [group]\n                       (every? #(= (->> % (into #{}) count) 9)\n                          group))]\n    (and (verify-group rows)\n         (verify-group cols)\n         (verify-group sqs))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "partitiono",
    "column" 1,
    "line" 394,
    "source"
    "(defne partitiono [a b c d]\n  ([[x . l] _ [x . l1] _]\n     (conda\n       ((project [x b]\n          (== (<= x b) true))\n        (partition l b l1 d))\n       (partition l b c d))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["n" "rows"]],
    "ns" "clojure.core.logic.bench",
    "name" "magic-diag",
    "column" 1,
    "line" 878,
    "source"
    "(defn magic-diag [n rows]\n  (first\n    (reduce\n      (fn [[r n] xs]\n        [(conj r (nth xs n)) (inc n)])\n      [[] 0]\n      rows)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.logic.bench",
    "name" "safefd",
    "column" 1,
    "line" 835,
    "source"
    "(defn safefd []\n  (run* [c1 c2 c3 c4 c5 c6 c7 c8 c9 :as vs]\n    (everyg #(fd/in % (fd/interval 1 9)) vs)\n    (fd/distinct vs)\n    (fd/eq\n     (= (- c4 c6) c7)\n     (= (* c1 c2 c3) (+ c8 c9))\n     (< (+ c2 c3 c6) c8)\n     (< c9 c8))\n    (project [vs]\n      (everyg (fn [[v n]] (fd/!= v n))\n        (map vector vs (range 1 10))))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["xs"]],
    "ns" "clojure.core.logic.bench",
    "name" "->rows",
    "column" 1,
    "line" 615,
    "source"
    "(defn ->rows [xs]\n  (->> xs (partition 9) (map vec) (into [])))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "connected",
    "column" 1,
    "line" 114,
    "source" "(defrel connected ^:index x ^:index y)",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["x" "l" "y"]],
    "ns" "clojure.core.logic.bench",
    "name" "digito",
    "column" 1,
    "line" 270,
    "source" "(defn digito [x l y]\n  (takeouto x l y))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"ns" "clojure.core.logic.bench",
    "name" "connected-to-allo",
    "line" 122,
    "column" 1,
    "doc" "Ensure that vertex v is connected to all vertices\n   vs.",
    "tag" nil,
    "source"
    "(defne connected-to-allo\n  \"Ensure that vertex v is connected to all vertices\n   vs.\"\n  [v vs]\n  ([_ ()])\n  ([_ [vh . vr]]\n     (connected v vh)\n     (connected-to-allo v vr)))",
    "file" "clojure/core/logic/bench.clj",
    "arglists" nil}
   {"arglists" [["n"]],
    "ns" "clojure.core.logic.bench",
    "name" "magic-grid",
    "column" 1,
    "line" 872,
    "source" "(defn magic-grid [n]\n  (repeatedly (* n n) lvar))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "checko",
    "column" 1,
    "line" 517,
    "source"
    "(defne checko [ws sl r n]\n  ;; if ws is empty, the first element of r must be n\n  ([() _ [a . _] a])\n  ;; otherwise we check the first weight\n  ([[w . wr] _ _ _]\n     (fresh [nsl nr]\n       ;; check the first weight with subchecko\n       (subchecko w sl r nr n)\n       ;; if it succeeds we add w to the new stone list\n       (conso w sl nsl)\n       ;; check remaining weights\n       (checko wr nsl nr n))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["n"]],
    "ns" "clojure.core.logic.bench",
    "name" "matches",
    "column" 1,
    "line" 530,
    "source"
    "(defn matches [n]\n  (run 1 [a b c d]\n    (fd/in a b c d (fd/interval 1 n)) \n    (fd/distinct [a b c d])\n    (== a 1)\n    (fd/<= a b) (fd/<= b c) (fd/<= c d)\n    (fd/eq (= (+ a b c d) n))\n    (checko [a b c d] () () n)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "moveo",
    "column" 1,
    "line" 367,
    "source"
    "(defne moveo [n x y z]\n  ([1 _ _ _]\n     (trace-lvars \"Move top disk from \" x)\n     (trace-lvars \" to \" y))\n  ([_ _ _ _]\n     (pred n #(> % 1))\n     (fresh [m _] (is m n dec)\n       (moveo m x z y) (moveo 1 x y _) (moveo m z y x))))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.logic.bench",
    "name" "cryptarithfd-2",
    "column" 1,
    "line" 329,
    "source" nil,
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "connected_2-x-index",
    "column" 1,
    "line" 114,
    "source" "(defrel connected ^:index x ^:index y)",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.logic.bench",
    "name" "dinesmanfd",
    "column" 1,
    "line" 412,
    "source"
    "(defn dinesmanfd []\n  (run* [baker cooper fletcher miller smith :as vs]\n    (fd/distinct vs)\n    (everyg #(fd/in % (fd/interval 1 5)) vs)\n    (fd/!= baker 5) (fd/!= cooper 1)\n    (fd/!= fletcher 5) (fd/!= fletcher 1)\n    (fd/< cooper miller) \n    (not-adjacento smith fletcher)\n    (not-adjacento fletcher cooper)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [[]],
    "ns" "clojure.core.logic.bench",
    "name" "cryptarithfd-1",
    "column" 1,
    "line" 316,
    "source" nil,
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "takeouto",
    "column" 1,
    "line" 266,
    "source"
    "(defne takeouto [x l y]\n  ([_ [x . y] _])\n  ([_ [h . t] [h . r]] (takeouto x t r)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" nil,
    "ns" "clojure.core.logic.bench",
    "name" "righto",
    "column" 1,
    "line" 68,
    "source"
    "(defne righto [x y l]\n  ([_ _ [x y . r]])\n  ([_ _ [_ . r]] (righto x y r)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}
   {"arglists" [["x" "l" "y"]],
    "ns" "clojure.core.logic.bench",
    "name" "first-digito",
    "column" 1,
    "line" 273,
    "source"
    "(defn first-digito [x l y]\n  (all\n   (digito x l y)\n   (a/> x 0)))",
    "file" "clojure/core/logic/bench.clj",
    "tag" nil}],
  "clojure.core.logic.arithmetic"
  [{"ns" "clojure.core.logic.arithmetic",
    "name" "<=",
    "macro" true,
    "line" 39,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro <= [x y]\n  \"Goal for testing whether x is less than or equal to y.\n  Non-relational.\"\n  `(fn [a#]\n     (let [wx# (walk a# ~x)\n           wy# (walk a# ~y)]\n       (if (clojure.core/<= wx# wy#)\n         a# nil))))",
    "file" "clojure/core/logic/arithmetic.clj",
    "arglists" [["x" "y"]]}
   {"ns" "clojure.core.logic.arithmetic",
    "name" ">=",
    "macro" true,
    "line" 22,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro >= [x y]\n  \"Goal for testing whether x is greater than or equal to y.\n  Non-relational.\"\n  `(fn [a#]\n     (let [wx# (walk a# ~x)\n           wy# (walk a# ~y)]\n       (if (clojure.core/>= wx# wy# )\n         a# nil))))",
    "file" "clojure/core/logic/arithmetic.clj",
    "arglists" [["x" "y"]]}
   {"ns" "clojure.core.logic.arithmetic",
    "name" ">",
    "macro" true,
    "line" 14,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro > [x y]\n  \"Goal for testing whether x is greater than y. Non-relational.\"\n  `(fn [a#]\n     (let [wx# (walk a# ~x)\n           wy# (walk a# ~y)]\n       (if (clojure.core/> wx# wy# )\n         a# nil))))",
    "file" "clojure/core/logic/arithmetic.clj",
    "arglists" [["x" "y"]]}
   {"ns" "clojure.core.logic.arithmetic",
    "name" "=",
    "macro" true,
    "line" 6,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro = [x y]\n  \"Goal for testing whether x and y are equal. Non-relational.\"\n  `(fn [a#]\n     (let [wx# (walk a# ~x)\n           wy# (walk a# ~y)]\n       (if (clojure.core/= wx# wy# )\n         a# nil))))",
    "file" "clojure/core/logic/arithmetic.clj",
    "arglists" [["x" "y"]]}
   {"ns" "clojure.core.logic.arithmetic",
    "name" "<",
    "macro" true,
    "line" 31,
    "column" 1,
    "tag" nil,
    "source"
    "(defmacro < [x y]\n  \"Goal for testing whether x is less than y. Non-relational.\"\n  `(fn [a#]\n     (let [wx# (walk a# ~x)\n           wy# (walk a# ~y)]\n       (if (clojure.core/< wx# wy# )\n         a# nil))))",
    "file" "clojure/core/logic/arithmetic.clj",
    "arglists" [["x" "y"]]}]},
 "description" "core.logic 0.8.4",
 "version" "0.8.4",
 "name" "clojure.core.logic",
 "group" "clojure.core.logic"}
