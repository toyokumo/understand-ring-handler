(ns understand-ring-handler.core
  (:gen-class)
  (:require [bidi.ring :as br]
            [muuntaja.middleware :as muu]
            [ring.adapter.jetty :as jetty]))

(defn- hello-handler [{:as req :keys [body-params]}]
  {:status 200
   :body body-params})

(defn- about-handler [req]
  {:status 200
   :headers {"Content-Type" "text/plain; charset=utf-8"}
   :body "弊社についてご紹介します。"})

(defn- not-found-handler [req]
  {:status 404
   :headers {"Content-Type" "text/plain; charset=utf-8"}
   :body "お探しのページは見つかりませんでした。"})

(def route
  ["/" {"hello" hello-handler
        "about" {:get about-handler}
        true not-found-handler}])

(defn- hoge-middleware
  "/hoge以外へのリクエストかつレスポンスが404ならいつもhogeを返すようにする"
  [handler]
  ; 関数が戻り値
  (fn [{:as req :keys [uri]}]                               ; リクエストマップの値を使える
    (let [{:as res :keys [status]} (handler req)            ; ここで引数のhandler(= 元々のhandler)を実行
          ]                                                 ; handlerの結果(= レスポンスマップ)に手を加えられる
      (if (and (= status 404) (not= uri "/hoge"))
        {:status 200
         :body "HOGE!"}
        res))))

(def handler
  (-> (br/make-handler route)                               ; handlerを作り、
      (hoge-middleware)                                     ; middlewareの引数として渡す
      (muu/wrap-format)                                     ; 追加
      ))

(defn -main
  [& args]
  (jetty/run-jetty handler {:host "localhost"
                            :port 8080
                            :join? false}))
