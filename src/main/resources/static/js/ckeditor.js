import {
	ClassicEditor,
	AccessibilityHelp,
	Autoformat,
	AutoImage,
	AutoLink,
	Autosave,
	Base64UploadAdapter,
	Bold,
	Code,
	Essentials,
	FontBackgroundColor,
	FontColor,
	FontFamily,
	FontSize,
	GeneralHtmlSupport,
	//Heading,
	HtmlComment,
	HtmlEmbed,
	ImageBlock,
	ImageCaption,
	ImageInline,
	ImageInsert,
	ImageInsertViaUrl,
	ImageResize,
	ImageStyle,
	ImageTextAlternative,
	ImageToolbar,
	ImageUpload,
	Italic,
	Link,
	LinkImage,
	List,
	ListProperties,
	Paragraph,
	PasteFromOffice,
	SelectAll,
	ShowBlocks,
	SimpleUploadAdapter,
	SourceEditing,
	Table,
	TableCaption,
	TableCellProperties,
	TableColumnResize,
	TableProperties,
	TableToolbar,
	TextTransformation,
	Undo,
	CKFinder, 
	CKFinderUploadAdapter
} from 'ckeditor5';

import translations from 'ckeditor5/translations/ko.js';

const editorConfig = {
	toolbar: {
		items: [
			'undo',
			'redo',
			'|',
			'sourceEditing',
			//'showBlocks',
			//'selectAll',
			'|',
			'fontSize',
			'fontFamily',
			'fontColor',
			'fontBackgroundColor',
			'|',
			'bold',
			'italic',
			//'code',
			'|',
			'link',
			'insertImage',
			'insertTable',
			'htmlEmbed',
			'|',
			'bulletedList',
			'numberedList',
			'|',
			'accessibilityHelp'
		],
		shouldNotGroupWhenFull: false
	},
	plugins: [
		AccessibilityHelp,
		Autoformat,
		AutoImage,
		AutoLink,
		Autosave,
		Base64UploadAdapter,
		Bold,
		Code,
		Essentials,
		FontBackgroundColor,
		FontColor,
		FontFamily,
		FontSize,
		GeneralHtmlSupport,
		//Heading,
		HtmlComment,
		HtmlEmbed,
		ImageBlock,
		ImageCaption,
		ImageInline,
		ImageInsert,
		ImageInsertViaUrl,
		ImageResize,
		ImageStyle,
		ImageTextAlternative,
		ImageToolbar,
		ImageUpload,
		Italic,
		Link,
		LinkImage,
		List,
		ListProperties,
		Paragraph,
		PasteFromOffice,
		SelectAll,
		ShowBlocks,
		SimpleUploadAdapter,
		SourceEditing,
		Table,
		TableCaption,
		TableCellProperties,
		TableColumnResize,
		TableProperties,
		TableToolbar,
		TextTransformation,
		Undo,
		CKFinder,
		CKFinderUploadAdapter
	],
	fontFamily: {
		supportAllValues: true
	},
	fontSize: {
		options: [10, 12, 14, 'default', 18, 20, 22, 28, 34, 48],
		supportAllValues: true
	},
	htmlSupport: {
		allow: [
			{
				name: /.*/,
				styles: true,
				attributes: true,
				classes: true
			}
		]
	},
	image: {
		toolbar: [
			'toggleImageCaption',
			'imageTextAlternative',
			'|',
			'imageStyle:inline',
			'imageStyle:wrapText',
			'imageStyle:breakText',
			'|',
			'resizeImage',
			'uploadImage'
		]
	},
	//initialData:
	//	'',
	language: 'ko',
	link: {
		addTargetToExternalLinks: true,
		defaultProtocol: 'http://',
		decorators: {
			toggleDownloadable: {
				mode: 'manual',
				label: 'Downloadable',
				attributes: {
					download: 'file'
				}
			}
		}
	},
	list: {
		properties: {
			styles: true,
			startIndex: true,
			reversed: true
		}
	},
	placeholder: '이 곳에 글을 적어 주세요!',
	table: {
		contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties']
	},
	translations: [translations],
	ckfinder : {
		uploadUrl : '/api/fileUploader?path=ckeditor'
	}
};

ClassicEditor.create(document.querySelector('#content'), editorConfig)
    .then(editor => {
        window.editor = editor;
    })
    .catch(error => {
        console.error(error);
    });
