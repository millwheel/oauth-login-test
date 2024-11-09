import 'package:flutter/material.dart';
import 'package:login_mobile/login.dart';

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
