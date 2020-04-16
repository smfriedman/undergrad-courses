
        <?php
            session_start();
            
            $file = $_GET['file'];

            
            //check for valid filename and username
            if( !preg_match('/^[\w_\.\-]+$/', $file) ){
                echo "Invalid filename";
                exit;
            }
             
            $name = $_SESSION['name'];
            if( !preg_match('/^[\w_\-]+$/', $name) ){
                echo "Invalid username";
                exit;
            }
            
            $full_path = sprintf('/home/stevenfriedman/user_files/%s/%s', $name, $file);

            // $finfo = finfo_open();
            // $mime = finfo_file($finfo, FILEINFO_MIME);
            // finfo_close($finfo);
            $finfo = new finfo(FILEINFO_MIME_TYPE);
            $mime = $finfo->file($full_path);         


            // if(!($mime == "text/plain" || $mime == "image/png" || $mime == "application/pdf")){

            // } else{

            // }
            //if($mime != "text/plain") header("Content-Type: " . $mime);

            $content = sprintf("Content-Type:%s", $mime);
            ob_clean();
            header($content);
            readfile($full_path);
        
        ?>

