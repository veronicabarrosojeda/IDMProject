/* global angular*/
(function () {
	'use strict';

	angular.module('FacturacionApp')
		.directive('dropzone', function () {
			return function(scope, element, attrs) {
				var config, dropzone, o, dz;
				
                                console.log(attrs);
                                
				config = scope[attrs.dropzone];
				
				o = config.options;
				
				//Default values
				o.url = o.url || 'http://localhost/upload/index.php';
				o.method = "post";
				o.addRemoveLinks		= angular.isDefined(o.addRemoveLinks)		? o.addRemoveLinks		: true;
				o.parallelUploads		= angular.isDefined(o.parallelUploads)		? o.parallelUploads		: 1;
				o.maxFilesize			= angular.isDefined(o.maxFilesize)			? o.maxFilesize			: 1; //1 MB
				o.maxFiles				= angular.isDefined(o.maxFiles)				? o.maxFiles			: null;
				o.uploadMultiple		= angular.isDefined(o.uploadMultiple)		? o.uploadMultiple		: false;
				o.autoProcessQueue		= angular.isDefined(o.autoProcessQueue)		? o.autoProcessQueue	: true;
				o.dictDefaultMessage	= angular.isDefined(o.dictDefaultMessage)	? o.dictDefaultMessage	: "Suelte un archivo aquí...";
				o.dictRemoveFile		= angular.isDefined(o.dictRemoveFile)		? o.dictRemoveFile		: "Eliminar";
				o.dictFileTooBig		= angular.isDefined(o.dictFileTooBig)		? o.dictFileTooBig		: "El archivo es demasiado grande";
				o.dictMaxFilesExceeded	= angular.isDefined(o.dictMaxFilesExceeded) ? o.dictMaxFilesExceeded: "Sólo se permite subir " + o.maxFiles + " archivos";
				
				
				config.eventHandlers['maxfilesexceeded'] = function (file) {
					//alert("No more files please!");
					this.removeFile(file);
				};
				
				// create a Dropzone for the element with the given options
				dropzone = new Dropzone(element[0], config.options);
				
				// bind the given event handlers
				angular.forEach(config.eventHandlers, function (handler, event) {
					dropzone.on(event, handler);
				});
				
				scope[attrs.dropzone] = dropzone;
			};
		});
})();