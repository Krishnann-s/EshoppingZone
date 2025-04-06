export function parseJwt(token) {
  try {
    // Split the token and get the payload (middle part)
    const base64Url = token.split(".")[1];
    // Replace characters for valid base64
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    // Decode the base64 string
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split("")
        .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
        .join("")
    );
    // Parse the JSON
    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error("Error parsing JWT token:", error);
    return null;
  }
}
