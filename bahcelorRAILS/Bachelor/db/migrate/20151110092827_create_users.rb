class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.boolean :doctor
      t.string :email
      t.string :gucid
      t.string :fname
      t.string :lname
      t.date :dob
      t.boolean :gender
      t.string :location
      t.string :uid
      t.string :avatar
      t.references :topic, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
