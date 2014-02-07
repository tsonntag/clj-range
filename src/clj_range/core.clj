(ns clj-range.core
  (:refer-clojure :exclude [range]))
"Clojure api for basic range functions like includes? overlaps?"
 
(defn range 
  ([fst lst] (range fst lst false compare))

  ([fst lst incl-or-cmp]
    (let [[incl cmp] (if (keyword? incl-or-cmp) 
                       [(case incl-or-cmp :inclusive true :exclusive false) compare]
                       [false incl-or-cmp])]
      (range fst lst incl cmp)))

  ([fst lst incl cmp] [fst lst incl cmp])

) 

(defn fst "returns first element of range" [[fst _ _ _]] fst)

(defn lst "returns last element of range" [[_ lst _ _]] lst)

(defn inclusive?  "returns true if range is inclusive" [[_ _ incl _]] incl)

(defn cmp  "returns compare fn for range" [[_ _ _ cmp]] cmp)

(defn includes? "returns true if x is in range"
   [[fst lst incl cmp :as range] x]
     (do; (println fst lst incl cmp :x x :inc (if incl <= <) (cmp fst x) (cmp x lst))
     (and (<= (cmp fst x) 0) ((if incl <= <) (cmp x lst) 0))))

(defn overlaps? "returns true ranges overlap"
   [[fst1 _ _ _ :as r1] [fst2 _ _ _ :as r2] & rest]
   (if (empty? rest)
     (or (includes? r2 fst1) (includes? r1 fst2))
     (or (overlaps? r1 r2) (some #(overlaps? r1 %) rest) (some #(overlaps? r2 %) rest))))



