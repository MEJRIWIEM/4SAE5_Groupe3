package tn.esprit.spring.wecare.Payloads.Responses;

public class TokenRefreshResponse {
	 private String accessToken;
	  private String refreshToken;
	  private String tokenType = "Bearer";
	  public TokenRefreshResponse(String accessToken, String refreshToken) {
	    this.setAccessToken(accessToken);
	    this.setRefreshToken(refreshToken);
	  }
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
