class ApplicationController < ActionController::API
  include ActionController::RespondWith
  include SentientController
  
  respond_to :json

  # (Custom responder)
  self.responder = HTTPErrors::Responder
  before_action :set_locale

  private

  def set_locale
    I18n.locale = http_accept_language.compatible_language_from(I18n.available_locales)
  end
end


