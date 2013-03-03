	Pouch('idb://employee4', function(err, db) {
                // Use db to call further functions
				db.replicate.from('http://127.0.0.1:9292/localhost:5984/employee4','idb://employee4', function (err, changes) {
					if (err) {
					    console.log(err);
					}
					else {
						console.log("Changes are: ");
						console.log(changes);
					}});
					$scope.loadTodos(response.rows);
            });
            
            var authors = [
              {name: 'Dal  e Harvey', commits: 253},
              {name: 'Mikeal Rogers', commits: 42},
              {name: 'Johannes J. Schmidt', commits: 13},
              {name: 'Randall Leeds', commits: 9}
            ];
            
            Pouch('idb://authors', function(err, db) {
              // Opened a new database
              db.bulkDocs({docs: authors}, function(err, results) {
                // Saved the documents into the database
                db.replicate.to('http://127.0.0.1:9292/localhost:5984/authors', function() {
                  // The documents are now in the cloud!
                  alert(' document is in the cloud! ');                  
                });
              });
            });
            
            var myCallback = function(){
            	alert(' done!  ');
            }
            
            Pouch.replicate('idb://authors', 'http://127.0.0.1:9292/localhost:5984/authors', myCallback)