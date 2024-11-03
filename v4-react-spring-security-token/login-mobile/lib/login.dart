import 'package:flutter/material.dart';

class LoginOptionsScreen extends StatelessWidget {
  const LoginOptionsScreen({super.key});

  final serverBaseUrl = "http://10.0.2.2:8080";

  void _loginWithProvider(String provider) {
    // 로그인 로직을 여기에 추가하세요.
    print("Login with $provider");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.deepPurpleAccent,
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back,
            size: 30,
          ),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: Text(
          "Select Login Provider",
          style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold),
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            LoginElevatedButton(
              text: "Login with Google",
              imagePath: 'assets/google_icon.png',
              onPressed: () => _loginWithProvider("google"),
            ),
            const SizedBox(height: 40),
            LoginElevatedButton(
              text: "Login with Naver",
              imagePath: 'assets/naver_icon.png',
              onPressed: () => _loginWithProvider("naver"),
            ),
            const SizedBox(height: 40),
            LoginElevatedButton(
              text: "Login with Kakao",
              imagePath: 'assets/kakao_icon.png',
              onPressed: () => _loginWithProvider("kakao"),
            ),
          ],
        ),
      ),
    );
  }
}

class LoginElevatedButton extends StatelessWidget {
  final String text;
  final String imagePath;
  final VoidCallback onPressed;

  const LoginElevatedButton({
    required this.text,
    required this.imagePath,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 350,
      child: ElevatedButton(
        onPressed: onPressed,
        style: ElevatedButton.styleFrom(
          padding: EdgeInsets.symmetric(vertical: 20.0, horizontal: 38.0),
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Image.asset(
              imagePath,
              width: 26,
              height: 26,
            ),
            SizedBox(
              width: 20,
            ),
            Text(
              text,
              style: TextStyle(fontSize: 24, color: Colors.black),
            ),
          ],
        ),
      ),
    );
  }
}
