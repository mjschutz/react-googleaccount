# react-native-googleaccount

This is a Android React Native module that make use of google-auth-library-java to get a JWT token from Service Account
to access Google Cloud APIs.

## Usage

```
import GoogleAccount from 'react-googleaccount'

GoogleAccount.fromString(
	JSON.stringify(require('./project-id.json'))),
	['https://www.googleapis.com/auth/drive']
).then((token) => {
	alert(token);
}).catch(console.err);

// Refresh/Get the current token after call of GoogleAccount.fromString
GoogleAccount.getAccessToken().then((token) => {
	alert(token);
}).catch(console.err);
```
