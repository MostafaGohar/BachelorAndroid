class User < ActiveRecord::Base
  belongs_to :topic
  has_many    :posts
  #has_many :follows, class_name: "Follow", foreign_key: :follower_id
  #has_many :follows, class_name: "Follow", foreign_key: :followee_id
  has_many :followers, class_name: 'Follow', :foreign_key => 'follower_id'
  has_many :followees, class_name: 'Follow', :foreign_key => 'followee_id'
  # validates :fname, format: { with: /\A[a-zA-Z]+\z/, message: "only allows letters" }
  # validates :lname, format: { with: /\A[a-zA-Z]+\z/, message: "only allows letters" }
  # validates :email, presence: true, uniqueness: true, format: {with: /\A\w+\-?\w*\.\w*\-?\w*@\w*\.?guc.edu.eg\z/,message: "insert GUC email"}
  # validates_inclusion_of :doctor, :in => [true, false]
  # validates :uid, presence: true, uniqueness: true
  # validate :dob_validation
  # def dob_validation 
  #   if !dob.nil? && dob > Date.today
  #   	errors.add(:dob, "can't be in the future")
  #   end  
  # end 
end
