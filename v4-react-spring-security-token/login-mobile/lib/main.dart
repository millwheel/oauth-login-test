import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Home(),
    );
  }
}

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  bool isLoggedIn = false;

  void _login() {
    // OAuth2 login logic here.
    setState(() {
      isLoggedIn = true;
    });
  }

  void _logout() {
    // OAuth2 logout logic here.
    setState(() {
      isLoggedIn = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(
              padding: const EdgeInsets.only(bottom: 50),
              child: Text(
                isLoggedIn
                    ? "Welcome! You are logged in"
                    : "You should logged in",
                style: TextStyle(fontSize: 30),
              ),
            ),
            SizedBox(
              width: 200,
              height: 60,
              child: ElevatedButton(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => LoginOptionsScreen()),
                  );
                },
                child: Text(
                  isLoggedIn ? "Logout" : "Login",
                  style: TextStyle(fontSize: 20),
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}

class LoginOptionsScreen extends StatelessWidget {
  const LoginOptionsScreen({super.key});

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
              onPressed: () => _loginWithProvider("Google"),
            ),
            const SizedBox(height: 30),
            LoginElevatedButton(
              text: "Login with Naver",
              imagePath: 'assets/naver_icon.png',
              onPressed: () => _loginWithProvider("Naver"),
            ),
            const SizedBox(height: 30),
            LoginElevatedButton(
              text: "Login with Kakao",
              imagePath: 'assets/kakao_icon.png',
              onPressed: () => _loginWithProvider("Kakao"),
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
    return ElevatedButton(
      onPressed: onPressed,
      style: ElevatedButton.styleFrom(
        padding: EdgeInsets.symmetric(vertical: 16.0, horizontal: 38.0),
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
    );
  }
}
