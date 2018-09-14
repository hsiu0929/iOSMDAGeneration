<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Landing Page - Start Bootstrap Theme</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template -->
    <link href="css/landing-page.css" rel="stylesheet">

  </head>

  <body>

    <!-- Navigation -->
    <nav class="navbar navbar-light bg-light static-top">
      <div class="container">
        <a class="navbar-brand" href="#">iOSGeneration</a>
      </div>
    </nav>

    <!-- Masthead -->
    <header class="masthead text-white text-center">
      <div class="overlay"></div>
      <div class="container">
        <div class="row">
          <div class="col-xl-10 mx-auto">
            <h2 class="mb-5">Please upload your UML file to generate iOS App project!</h2>
          </div>
          <div class="col-xl-8 mx-auto">
            <h5 class="mb-5">
              If you need more help, go to
              <a href="guides_install.jsp">Guides</a>
              .
            </h5>
          </div>
          <div class="col-md-10 col-lg-8 col-xl-10 mx-auto">
            <div class="row">
              <div class="card text-dark text-left col-md-10 col-lg-12 mx-auto" style="width: 18rem; background: rgba(242, 242, 242, 0.7);">
                <div class="card-body">
                  <form class="needs-validation" method="post" action="fileUploadServlet" enctype="multipart/form-data">
                    <div class="row">
                      <div class="col-md-12 mb-3">
                        <label for="iOSApplicationName">iOS Project Name</label>
                        <input type="text" class="form-control" name="iOSProjectName" id="iOSApplicationName" placeholder="Please input iOS App Name..." value="" required>
                        <div class="invalid-feedback">Valid iOSApplicationName is required.</div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="input-group col-md-12 mb-3">
                        <div class="input-group-prepend">
                          <span class="input-group-text">Upload</span>
                        </div>
                        <div class="custom-file">
                          <input type="file" name="file" class="custom-file-input" id="file" value="" required>
                          <label class="custom-file-label" for="FILE">Choose file</label>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <button class="btn btn-primary btn-lg btn-block" type="submit">Submit</button>
                    </div>
                  </form>
  
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
  

    <!-- Footer -->
    <footer class="footer bg-light">
      <div class="container">
        <div class="row">
          <div class="col-lg-6 h-100 text-center text-lg-left my-auto">
            <p class="text-muted small mb-4 mb-lg-0">&copy; © FCU SELab 2018. All Rights Reserved.</p>
          </div>
          <div class="col-lg-6 h-100 text-center text-lg-right my-auto">
            <ul class="list-inline mb-0">
              <li class="list-inline-item mr-3">
                <a href="#">
                  <i class="fa fa-facebook fa-2x fa-fw"></i>
                </a>
              </li>
              <li class="list-inline-item mr-3">
                <a href="#">
                  <i class="fa fa-twitter fa-2x fa-fw"></i>
                </a>
              </li>
              <li class="list-inline-item">
                <a href="#">
                  <i class="fa fa-instagram fa-2x fa-fw"></i>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </footer>

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script>
    	$('#file').on('change', function() {
			//get the file name
			var fileName = $(this).val().split('\\').pop();
			//replace the "Choose a file" label
			$(this).next('.custom-file-label').html(fileName);
		});

    </script>

  </body>

</html>
