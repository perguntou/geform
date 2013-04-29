package br.ufrj.softwaresmartphone.geform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import br.ufrj.softwaresmartphone.util.Item;
import br.ufrj.softwaresmartphone.util.Options;
import br.ufrj.softwaresmartphone.util.Options.ChoiceType;

/**
 * 
 */
public class EditItemActivity extends FragmentActivity {

	private Item m_item;

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		m_item = getIntent().getParcelableExtra( "item" );

		setContentView( R.layout.activity_edit_item );

		final EditText questionEditText = ((EditText) findViewById( R.id.item_question ));
		final EditText positionEditText = ((EditText) findViewById( R.id.item_position ));
		final EditOptionsFragment editOptionsFragment = (EditOptionsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_edit_options );
		final Spinner itemTypeSpinner = ((Spinner) findViewById( R.id.item_type )); 

		positionEditText.setText( String.valueOf( getIntent().getIntExtra( "requestPosition", 1 ) ) );
		questionEditText.setText( m_item.getQuestion() );
		int type = Constants.TYPE_TEXT;
		if( m_item.hasOptions() ) {
			ChoiceType mode = m_item.getOptions().getChoiceType();
			if( mode.equals( ChoiceType.single ) ) {
				type = Constants.TYPE_SINGLE_CHOICE;
			} else
			if( mode.equals( ChoiceType.multiple ) ) {
				type = Constants.TYPE_MULTIPLE_CHOICE;
			}
		}
		itemTypeSpinner.setSelection( type );

		itemTypeSpinner.setOnItemSelectedListener( new OnItemSelectedListener() {
			@Override
			public void onItemSelected( AdapterView<?> adapter, View view, int position, long id ) {
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				if( position == Constants.TYPE_TEXT ) {
					transaction.hide( editOptionsFragment );
				} else {
					transaction.show( editOptionsFragment );
				}
				transaction.commit();
			}

			@Override
			public void onNothingSelected( AdapterView<?> adapter ) {}
		} );

		((Button) findViewById( R.id.button_item_ok )).setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				m_item.setQuestion( questionEditText.getText().toString().trim() );

				if( !(m_item.getQuestion().equals("")) ) {
					if( editOptionsFragment.isVisible() ) {
						Options options = editOptionsFragment.getOptions();
						switch( itemTypeSpinner.getSelectedItemPosition() ) {
//						case Constants.TYPE_TEXT:
//							options.clear();
//							break;
						case Constants.TYPE_SINGLE_CHOICE:
							options.setChoiceType( ChoiceType.single );
							break;
						case Constants.TYPE_MULTIPLE_CHOICE:
							options.setChoiceType( ChoiceType.multiple );
							break;
						}
						m_item.setOptions( options );
					} else {
						m_item.getOptions().clear();
					}
					Intent intent = getIntent();
					intent.putExtra( "resultPosition", Integer.parseInt( positionEditText.getText().toString() ) );
					intent.putExtra( "item", m_item );
					setResult( Activity.RESULT_OK,  intent );
					finish();
				}
			}
		} );

	}

	/**
	 * Returns the edited item
	 * @return the item
	 */
	public Item getItem() {
		return m_item;
	}

}
