package android.microntek.f1x.mtcdtools.activities.named.objects;

import android.microntek.f1x.mtcdtools.R;
import android.microntek.f1x.mtcdtools.adapters.ActionsInSequenceArrayAdapter;
import android.microntek.f1x.mtcdtools.adapters.entries.ActionInSequenceEntry;
import android.microntek.f1x.mtcdtools.named.NamedObject;
import android.microntek.f1x.mtcdtools.named.NamedObjectId;
import android.microntek.f1x.mtcdtools.named.objects.actions.BroadcastIntentAction;
import android.microntek.f1x.mtcdtools.named.objects.actions.KeyAction;
import android.microntek.f1x.mtcdtools.named.objects.actions.LaunchAction;
import android.microntek.f1x.mtcdtools.named.objects.actions.StartIntentAction;
import android.microntek.f1x.mtcdtools.named.objects.containers.ActionsSequence;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by f1x on 2017-02-05.
 */

public class ActionsSequenceActivity extends NamedObjectsContainerActivity {
    public ActionsSequenceActivity() {
        super(R.layout.activity_actions_sequence_details);
    }

    @Override
    protected void initControls() {
        super.initControls();
        mNamedObjectIdsArrayAdapter.setObjectTypeFilters(new TreeSet<>(Arrays.asList(KeyAction.OBJECT_TYPE, LaunchAction.OBJECT_TYPE, BroadcastIntentAction.OBJECT_TYPE, StartIntentAction.OBJECT_TYPE)));
        mActionsInSequenceArrayAdapter = new ActionsInSequenceArrayAdapter(this);

        ListView addedActionsListView = (ListView) this.findViewById(R.id.listViewAddedNamedObjects);
        addedActionsListView.setAdapter(mActionsInSequenceArrayAdapter);
        addedActionsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                mActionsInSequenceArrayAdapter.removeAt(position);

                return true;
            }
        });

        Button addNamedObjectButton = (Button) this.findViewById(R.id.buttonAddNamedObject);
        addNamedObjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NamedObjectId actionId = (NamedObjectId) mNamedObjectsSpinner.getSelectedItem();
                ActionInSequenceEntry actionInSequenceEntry = new ActionInSequenceEntry(actionId, 0);
                mActionsInSequenceArrayAdapter.add(actionInSequenceEntry);
            }
        });
    }

    @Override
    protected void fillControls(NamedObject namedObject) {
        super.fillControls(namedObject);
        if (namedObject.getObjectType().equals(ActionsSequence.OBJECT_TYPE)) {
            ActionsSequence actionsSequence = (ActionsSequence) namedObject;
            mActionsInSequenceArrayAdapter.reset(actionsSequence.getActionDelays());
        }
    }

    @Override
    protected NamedObject createNamedObject(NamedObjectId namedObjectId) {
        List<NamedObjectId> actionNames = new ArrayList<>();
        List<Map.Entry<NamedObjectId, Integer>> actionDelays = new ArrayList<>();

        for (int i = 0; i < mActionsInSequenceArrayAdapter.getCount(); ++i) {
            ActionInSequenceEntry actionInSequenceEntry = mActionsInSequenceArrayAdapter.getItem(i);
            actionNames.add(actionInSequenceEntry.getActionId());
            actionDelays.add(new AbstractMap.SimpleEntry<>(actionInSequenceEntry.getActionId(), actionInSequenceEntry.getDelay()));
        }

        return new ActionsSequence(namedObjectId, actionNames, actionDelays);
    }

    private ActionsInSequenceArrayAdapter mActionsInSequenceArrayAdapter;
}
