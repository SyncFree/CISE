; State = Obj -> Val
; State : c -> Int
; sigma \in State
(declare-fun c () Int)

; invariant
(define-fun I ((s Int)) Bool 
  (>= s 0))

; operation inc
(define-fun inc ((s Int)) Int 
  (+ s 1)
)

; operation dec 
(define-fun dec1 ((s Int)) Int 
  (- s 1)
)
(define-fun P_dec1 ((s Int)) Bool 
  (> s 0)
)

(define-fun dec2 ((s Int)) Int 
  s
)
(define-fun P_dec2 ((s Int)) Bool 
  (<= s 0)
)

; guarentee G = G0 \cup skip
(define-fun G0 ((s Int)) Int 
	(+ s 1)
)
  
; relation G
(define-fun G ((s Int) (s1 Int)) Bool
  (or (= s s1) (= (G0 s) s1))
)

; relation G_dec1 
(define-fun G_dec1 ((s Int) (s1 Int)) Bool
  (= (dec1 s) s1)
)

; COROLLARY 31
; predicates Q_o,i = P_o,i

; operation dec
; checks stable(P_dec1, G0) 
(push)
(assert (not (forall ((c Int)) 
  (=> (P_dec1 c) (P_dec1 (G0 c))))))


