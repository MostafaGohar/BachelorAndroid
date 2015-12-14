# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20151110094423) do

  create_table "categories", force: :cascade do |t|
    t.string   "name",       limit: 255
    t.datetime "created_at",             null: false
    t.datetime "updated_at",             null: false
  end

  create_table "comments", force: :cascade do |t|
    t.integer  "user_id",    limit: 4
    t.text     "content",    limit: 65535
    t.integer  "post_id",    limit: 4
    t.datetime "created_at",               null: false
    t.datetime "updated_at",               null: false
  end

  add_index "comments", ["post_id"], name: "index_comments_on_post_id", using: :btree
  add_index "comments", ["user_id"], name: "index_comments_on_user_id", using: :btree

  create_table "follows", force: :cascade do |t|
    t.integer  "follower_id", limit: 4
    t.integer  "followee_id", limit: 4
    t.datetime "created_at",            null: false
    t.datetime "updated_at",            null: false
  end

  add_index "follows", ["followee_id"], name: "followee_id", using: :btree
  add_index "follows", ["follower_id"], name: "follower_id", using: :btree

  create_table "posts", force: :cascade do |t|
    t.integer  "desttype",   limit: 4
    t.integer  "user_id",    limit: 4
    t.integer  "destid",     limit: 4
    t.text     "content",    limit: 65535
    t.string   "title",      limit: 255
    t.datetime "created_at",               null: false
    t.datetime "updated_at",               null: false
  end

  add_index "posts", ["user_id"], name: "index_posts_on_user_id", using: :btree

  create_table "topics", force: :cascade do |t|
    t.string   "name",        limit: 255
    t.integer  "category_id", limit: 4
    t.text     "description", limit: 65535
    t.datetime "created_at",                null: false
    t.datetime "updated_at",                null: false
  end

  add_index "topics", ["category_id"], name: "index_topics_on_category_id", using: :btree

  create_table "users", force: :cascade do |t|
    t.boolean  "doctor"
    t.string   "email",      limit: 255
    t.string   "gucid",      limit: 255
    t.string   "fname",      limit: 255
    t.string   "lname",      limit: 255
    t.date     "dob"
    t.boolean  "gender"
    t.string   "location",   limit: 255
    t.string   "uid",        limit: 255
    t.string   "avatar",     limit: 255
    t.integer  "topic_id",   limit: 4
    t.datetime "created_at",             null: false
    t.datetime "updated_at",             null: false
  end

  add_index "users", ["topic_id"], name: "index_users_on_topic_id", using: :btree

  add_foreign_key "comments", "posts"
  add_foreign_key "comments", "users"
  add_foreign_key "follows", "users", column: "followee_id", name: "follows_ibfk_2"
  add_foreign_key "follows", "users", column: "follower_id", name: "follows_ibfk_1"
  add_foreign_key "posts", "users"
  add_foreign_key "topics", "categories"
  add_foreign_key "users", "topics"
end
