class Topic < ActiveRecord::Base
  belongs_to :category
  has_one :user
end
