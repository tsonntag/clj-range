# clj-range

A Clojure library for basic range operations.

## Installation

`clj-range` is available as a Maven artifact via [Clojars](http://clojars.org/clj-range).

## Usage

The main namespace for range operations in the `clj-range` library is `clj-range.core`.

    => (require '[clj-range.core :as r])

Create a range with range, specifying the first and last element:

    => (r/range 0 10)

The first element is inclusive and the last element is exclusive by default:

    => (r/includes? (r/range 0 10) 10)
       false

Create a range with the last element being exclusive:

    => (r/includes? (r/range 0 10 :inclusive 10)
       true

Default comparator is compare from clojure.core.

You may provide your own comparator:

    => (r/range 0 10 my-compare)

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

User overlaps? to check for overlaps:

    => (r/overlaps? (r/range 0 10) (r/range 10 20)
    false

    => (r/overlaps? (r/range 0 10 :inclusive) (r/range 10 20)
    true

    => (r/overlaps? (r/range 0 10) (r/range 5 20)
    true

## License

Copyright © 2014 Thomas Sonntag 

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
