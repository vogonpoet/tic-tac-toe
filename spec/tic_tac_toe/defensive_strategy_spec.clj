(ns tic-tac-toe.defensive-strategy-spec
  (:use [speclj.core]
        [tic-tac-toe.defensive-strategy]))


(describe 
  "Making a move for the computer" 

  (it "wins if it can"
      (should= 2
               (find-computer-move [:x :x nil 
                                    :o nil nil 
                                    nil nil :o] :x))
      (should= 4
               (find-computer-move [:x :o nil 
                                    :o nil :o 
                                    :o :x :x] :x)))

  (it "blocks if it needs to"
      (should= 2
               (find-computer-move [:o :o nil
                                    :x nil nil
                                    nil :x nil] :x)))

  (it "chooses winning over blocking"
      (should= 8
               (find-computer-move [nil nil nil 
                                    :o :o nil
                                    :x :x nil] :x)))

  (it "chooses the middle if available"
      (should= 4
               (find-computer-move [:o  nil nil
                                    nil nil nil
                                    nil nil nil] :x)))

  (it "chooses an empty corner if the middle is taken and there is no potential trap"
      (should= 0
               (find-computer-move [nil nil nil
                                    nil :o  nil
                                    nil nil nil] :x))

      (should= 2
               (find-computer-move [:x  nil nil
                                    nil :o  nil
                                    nil nil nil] :x)) )

  (it "can detect a lonely oppent"
      (should (lonely-opponent? [:o nil nil] :x))
      (should (lonely-opponent? [nil :o nil] :x))
      (should (lonely-opponent? [:o :o nil] :x))
      (should-not (lonely-opponent? [nil nil nil] :x))
      (should-not (lonely-opponent? [:o nil :x] :x))
      (should-not (lonely-opponent? [nil nil :x] :x)))

  (it "can find which row the corner is in"
      (should= [:x nil nil]
               (containing-row [:x  nil nil
                                :o  nil nil
                                nil nil nil] 2)))

  (it "can find which column the corner is in"
      (should= [nil nil :o]
               (containing-column [:x  nil nil
                                   :o  nil nil
                                   nil nil :o] 2)))

  (it "can detect a potential corner trap"
      (should (is-trappable? [nil nil nil
                              nil :x  :o
                              :o  nil nil] 8 :x)))

  (it "prevents the corner trap"
      (should= 0
               (find-computer-move [nil :o  nil
                                    nil :x  nil
                                    :o  nil nil] :x))

      (should= 2
               (find-computer-move [nil :o  nil
                                    nil :x  nil
                                    nil nil :o] :x))

      (should= 8
               (find-computer-move [nil nil nil
                                    nil :x  :o
                                    :o  nil nil] :x))

      (should= 2
               (find-computer-move [:o  nil nil
                                    nil :x  :o
                                    nil nil nil] :x))

      (should= 6
               (find-computer-move [:o  nil nil
                                    nil :x  nil
                                    nil :o  nil] :x))

      (should= 8
               (find-computer-move [nil nil :o
                                    nil :x  nil
                                    nil :o  nil] :x))

      (should= 0
               (find-computer-move [nil nil :o
                                    :o  :x  nil
                                    nil nil  nil] :x))

      (should= 6
               (find-computer-move [nil nil nil 
                                    :o  :x  nil
                                    nil nil :o] :x)))

  (it "responds to the diagonal trap"
      (should (#{1 3 5 7}
                  (prevent-diagonal-trap :x 
                                         [:o  nil nil
                                          nil :x  nil
                                          nil nil :o])))
      (should (#{1 3 5 7}
                  (prevent-diagonal-trap :x 
                                         [nil  nil :o
                                          nil :x  nil
                                          :o nil nil])))
      (should= nil (prevent-diagonal-trap :x
                                          [nil nil :o
                                           nil :x  nil
                                           nil nil nil])))

  (it "prevents to the diagonal trap"
      (should= 1
               (find-computer-move [:o  nil nil
                                    nil :x  nil
                                    nil nil :o] :x))))

