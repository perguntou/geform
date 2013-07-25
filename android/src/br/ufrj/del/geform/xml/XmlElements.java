/**
 * 
 */
package br.ufrj.del.geform.xml;


/**
 * 
 */
public class XmlElements {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 */
	public static enum Attribute {
		TYPE("type");

		private final String attribute;

		Attribute( final String attribute ) {
			this.attribute = attribute;
		}

		public static Attribute fromString( final String value ) {
			for( Attribute att : Attribute.values() ) {
				final String stringAttribute = att.toString();
				if( stringAttribute.equals( value ) ) {
					return att;
				}
			}
			final String throwMessage = String.format( "%s is not a valid Attribute", value );
			throw new IllegalArgumentException( throwMessage );
		}

		@Override
		public String toString() {
			return attribute;
		}

	}

	/**
	 *
	 */
	public static enum Tag {
		FORM( "form" ),
		TITLE( "title" ),
		AUTHOR( "author" ),
		DESCRIPTION( "description" ),
		TIMESTAMP( "timestamp" ),
		ITEM( "item" ),
		QUESTION( "question" ),
		OPTIONS( "options" ),
		OPTION( "option" );

		private final String tag;

		Tag( final String tag ) {
			this.tag = tag;
		}

		public static Tag fromString( final String value ) {
			for( Tag tag : Tag.values() ) {
				final String stringTag = tag.toString();
				if( stringTag.equals( value ) ) {
					return tag;
				}
			}
			final String throwMessage = String.format( "%s is not a valid Tag", value );
			throw new IllegalArgumentException( throwMessage );
		}

		@Override
		public String toString() {
			return tag;
		}

	}
}