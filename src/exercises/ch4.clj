(ns fwpd.ch4)
(def filename "suspects.csv")
(def db (mapify (parse (slurp filename))))

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cunnen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
    rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;; Ex 1 return list of names
(defn glitter-names
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))

;; Ex 2 function to append suspects
(defn append-suspect
  [records new-suspect]
  (conj records new-suspect))

;; Ex 3 function to validate input of Ex 2
(defn validate
  [keywords record]
  (every? record keywords))

(defn safe-append-suspect
  [records new-suspect keywords]
  (if (validate keywords new-suspect)
    (conj records new-suspect)
    (println
     (clojure.string/join ", " (map str (keys new-suspect)))
     "provided as keywords, should be :name and :glitter-index")))

;; Ex 4 function to convert back to csv
;; Head and tail strategy

(defn to-csv
  [data]
  (reduce))
