# Changelog

All notable changes to this project will be documented in this file.

## 2025-07-11
### 🚀 Added
- docs(CHANGELOG): Add CHANGELOG.md file
- build(nginx): Set user as root in supervisord.conf
- build(nginx): Add supervisord.conf and set application as prod profile

### 🛠️ Changed
- build(nginx): Trying to use nginx on render by dockerfile

## 2025-07-10
### 🚀 Added
- docs(README): Update README.md
- style(form): Add Password Visibility Toggle in AuthForm and ResetPassword
- style(errors): Using toasts to show errors messages
- feat(environment): Add frontend base url variable

### 🐛 Fixed
- fix(lint): Solving any type in ResetPassword page
- fix(lint): Solving any type in reaset and forget pages

## 2025-07-09
### 🚀 Added
- test(AuthController): Fix old tests and add forger and reset password tests
- feat(controller): Implementing forget and reset password in UserController
- feat(forgetPassword): Add forget password elemento to login in AuthForm
- feat(app): Add forget and reset password routes to app
- feat(api): Add forget password and reset password methods to api
- feat(page): Add Reset Password page
- feat(page): Add Forget Password page
- feat(forgetPassword): Add ResetPasswordRequest dto
- feat(forgetPassword): Add ForgetPasswordRequest dto
- feat(forgetPassword): Add sendPasswordResetEmail method to EmailService
- feat(UserService): Add generatePasswordResetToken and resetPassword methods
- feat(models): Update user model to add forget password properties
- feat(migrations): Add reset password columns in database
- feat(errors): Add not confirmed email error in AuthForm for register
- feat(UserService): Add error for already creted users
- feat(emailConfirmation): Add email confirmation routes on UserController
- feat(emailConfirmation): Add token logic on user service
- feat(AuthContext): Add email confirmation validation logic on AuthContext
- feat(errors): Add not confirmed email error in AuthForm
- feat(route): Add email confirmation route on app.tsx
- feat(lib): Add email confirmation function
- feat(page): Add email confirmation page
- feat(models): Update user model to add email confirmation properties

### 🛠️ Changed
- style(emailService): Update reset password email view
- style(emailService): Update comfirm email route view
- style(emailService): Update email view
- build(properties): Update properties

### 🐛 Fixed
- fix(tests): Fix UserController and UserService tests

### ⚠️ Security
- feat(UserService): Add generatePasswordResetToken and resetPassword methods

## 2025-07-08
### 🛠️ Changed
- build(properties): COnfiguring flyway to slipt dev and prod migration
- refactor(flyway): Slipt prod and dev migrations

### 🗑️ Removed
- perf(migrations): Removing unused migrations

## 2025-07-05
### 🚀 Added
- feat(admin): add admin workflow
- feat(statementManager): Add statement manager page
- feat(textarea): Add text are ui component

### 🐛 Fixed
- fix(tests): Fix caches dependeces in frontend ci test
- fix(turborepo): Replace papeline to tasks key

## 2025-07-04
### 🚀 Added
- feat(usercontroller): Add delete route /api/users/me
- feat(profile): Add profele page
- feat(form): Improving password validation
- feat(form): Add min characteres password on register
- feat(api): Connect statements to api
- feat(api): Connect leadboard and gamehistory to api
- feat(api): Connect login and register with api
- feat(api): create api connect file
- build(npm): Add axios dependence

### 🛠️ Changed
- feat(form): Improving password validation

### 🐛 Fixed
- fix(variables): Fix variables names in multiple files
- fix(leadboard): Fix leadboard load
- fix(form): Fix indentation in AuthForms
- fix(npm): Fix corrupted package-lock.json
- fix(index): Including game in index page
- fix(leaderBoard): Fix getLeaderBoard function
- fix(layout): Change high_score to highScore

## 2025-07-03
### 🚀 Added
- build(turborepo): Adding package manager to turborepo
- docs(README): Update readme

### 🛠️ Changed
- fix(field): Use username to login insert of email

### 🐛 Fixed
- fix(test): fix controller tests
- fix(StatementControllerTest): Adding missing impots
- fix(GameHistoryController): Change is forbiden to is unauthorized 403 to 401
- fix(GameHistoryController): Add missing importantion

### Security
- test(StatementController): Add Security config to test
- test(StatementController): Add jwt mock
- test(GameHistoryController): Add jwt mock
- test(controllers): Removing security filter on tests
- test(AuthController): Reoving security filter on test

## 2025-07-02
### 🚀 Added
- build(pom): Update dependences
- feat(api): Connect frontend with backend api
- feat(config): Configuring backend url based on environment
- build(deploy): Add vercel.json file
- build(pom): Add test profileto pom

### 🐛 Fixed
- fix(variables): Removing default variables
- fix(env): Add path to env file

### ⚠️ Security
- feat(security): Add cors config

## 2025-07-01
### 🚀 Added
- feat(user): Set USER role as default on register
- feat(migrations): Add not null to role property in table user
- feat(application.properties): Configuring prod aplication properties
- feat(route): Add deleteUser route

### ⚠️ Security
- feat(security): Update UserDeatilsService to verify role
- feat(security): Update routes auth access
- feat(security): Add auth requeriment for /api/users/me
- feat(dto): Update userResponse to return user role
- feat(models): Update user model to add role property
- feat(migrations): Add role to user table

## 2025-06-30
### 🚀 Added
- feat(application.properties): Set spring.jpa.open-in-view as false
- build(pom): Add suport for profiles in pom.xml

### 🐛 Fixed
- fix(models): Forcing explanation to be text
- fix(database): Removing @Lob from statement column
- fix(database): Add type to statement column

### ⚠️ Security
- build(envionment): Add jwt expriration to application-prod.properties

## 2025-06-29
### 🚀 Added
- perf(eslint): Solving eslint warnings
- style(NotFound): Update not found page style
- ci(tests): add test workflow

### 🐛 Fixed
- fix(eslint): Fixing missing comma in eslint.config.js
- fix(test): fix expeted result
- fix(route): fix userId parameter
- fix(route): fix user route

### ⚠️ Security
- feat(security): Secure POST /api/gamehistory endpoint

## 2025-06-28
### 🚀 Added
- docs(README): Update readme
- docs(LICENSE): Add MIT license file
- docs(README): Create a readme

### 🐛 Fixed
- fix(dotenv): Add dotenv load in main() on test envioroment
- fix(dotenv): Add dotenv load in main()

## 2025-06-27
### 🚀 Added
- feat: Initial commit of the Backend application
- ci(.gitignore): Update .gitignore file

## 2025-06-11
### 🚀 Added
- docs(README): Update readme
- ci(.gitignore): Add gitignore file

## 2025-05-29
### 🚀 Added
- style(button): Update Sign In Button
- feat(components): Add main components

### 🐛 Fixed
- fix(postcss): Solving postcss comflite

## 2025-05-28
### 🚀 Added
- ci(husky): Add husky setup
- feat(vite): Setup frontend
- ci(.gitignore): Add gitignore file
- feat(data): Add MySQL schemas