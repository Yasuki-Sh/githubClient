# GitHub API連携練習アプリ

GitHub APIを利用して、プライベートリポジトリの一覧取得および詳細表示を行うAndroidアプリです。

## 機能概要
- **プライベートリポジトリの取得・一覧表示**
  - settings画面にてトークンを設定するとプライベート。ユーザー名の場合パブリックリポジトリを取得、表示
- **リポジトリ詳細情報の閲覧**
  - リポジトリの選択により、詳細説明と README を表示

## 開発ステータス・今後の予定
- [x] **API連携の実装**
- [x] **ユーザー入力対応**（アプリ内でユーザー名やトークンを入力・保持）
- [x] **エラーハンドリング強化**

## 技術スタック
- **Language:** Kotlin
- **UI:** Android View / ViewBinding
- **Networking:** Retrofit2 / OkHttp3
- **Serialization:** Kotlin Serialization
- **Local Storage:** DataStore
- **Security:** Tink

## 動作確認手順
