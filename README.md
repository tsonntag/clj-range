# clj-range

A Clojure library for basic range operations.

## Installation

`clj-range` is available as a Maven artifact via [Clojars](http://clojars.org/clj-range).

## Usage

The main namespace for range operations in the `clj-range` library is `clj-range.core`.

    => (require '[clj-range.core :as r])

Create a range with range, specifying the first and last element:

    => (r/range 0 10)

Access the first and last element with fst and lst:

    => (r/fst (r/range 0 10))
       0

    => (r/lst (r/range 0 10))
       10

The first element is inclusive and the last element is exclusive by default:

    => (r/inclusive? (r/range 0 10)) 
       false

    => (r/includes? (r/range 0 10) 10)
       false

Create a range with the last element being inclusive:

    => (r/inclusive? (r/range 0 10 :inclusive)) 
       true

    => (r/includes? (r/range 0 10 :inclusive) 10)
       true

Default comparator is compare from clojure.core.

You may provide your own comparator:

    => (r/range 0 10 my-compare)

Access the comparator with cmp:

    => (r/cmp (r/range 0 10 my-compare))
       my-compare

The :inclusive keyword is expected before the comparator:

    => (r/range 0 10 :inclusive my-compare)

Use includes? to check if an element is within the range:

    => (r/includes? (r/range 0 10) 5 
    true

    => (r/includes? (r/range 0 10) 15 
    false

    => (r/includes? (r/range 0 10) 10 
    false

    => (r/includes? (r/range 0 10 :inclusive) 10 
    true

You may use a vector instead of r/range:

    => (r/includes? [0 10] 5 
    true

    => (r/includes? [0 10] 15 
    false

    => (r/includes? [0 10] 10 
    false

    => (r/includes? [0 10 true] 10 
    true

Use overlaps? to check for overlaps:

    => (r/overlaps? (r/range 0 10) (r/range 10 20)
    false

    => (r/overlaps? (r/range 0 10 :inclusive) (r/range 10 20)
    true

    => (r/overlaps? (r/range 0 10) (r/range 5 20)
    true

    => (r/overlaps? (r/range 0 20) (r/range 5 10)
    true

overlaps? also works for more than two ranges:

    => (r/overlaps? (r/range 0 10) (r/range 20 30) (r/range 40 50))
    false

    => (r/overlaps? (r/range 0 20) (r/range 10 30) (r/range 40 50))
    true

overlaps? also works for vectors:

    => (r/overlaps? [0 10] [5 20]
    true

## License

Copyright © 2014 Thomas Sonntag 

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
