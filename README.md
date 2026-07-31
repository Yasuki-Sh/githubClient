# GitHub API連携練習アプリ

GitHub APIを利用して、プライベートリポジトリの一覧取得および詳細表示を行うAndroidアプリです。

## 機能概要
- **プライベートリポジトリの取得・一覧表示**
  - `local.properties` に設定した個人アクセストークンを使用して認証・取得
- **リポジトリ詳細情報の閲覧**
  - リポジトリの選択により、詳細説明と README を表示

## 開発ステータス・今後の予定
- [x] **API連携の実装**（現状はユーザーをハードコードして動作確認中）
- [ ] **ユーザー入力対応**（アプリ内でユーザー名やトークンを入力・保持できるよう実装予定）

## 技術スタック
- **UI:** Android View / ViewBinding
- **Networking:** Retrofit2 / OkHttp3
- **Serialization:** Kotlin Serialization
- **Language:** Kotlin

## 動作確認手順
`local.properties` に GitHub Personal Access Token を設定してください。
```properties
accessToken=your_token_here
