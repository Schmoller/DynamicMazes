package au.com.mineauz.dynmazes;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.util.BlockVector;

import au.com.mineauz.dynmazes.misc.Callback;
import au.com.mineauz.dynmazes.misc.MassBlockUpdater;
import au.com.mineauz.dynmazes.misc.Notifiable;
import au.com.mineauz.dynmazes.styles.StoredBlock;

public class FillTask<T extends INode> extends Notifiable
{
	private World mWorld;
	private BlockVector mMin;
	private BlockVector mMax;
	private StoredBlock mType;
	private MassBlockUpdater mUpdater;
	
	public FillTask(BlockVector min, BlockVector max, World world, StoredBlock type)
	{
		this(min, max, world, type, null);
	}
	
	public FillTask(BlockVector min, BlockVector max, World world, StoredBlock type, Callback callback)
	{
		super(callback);
		
		mMin = min;
		mMax = max;
		mWorld = world;
		
		mType = type;

		mUpdater = MassBlockUpdater.create();
	}
	
	public void start()
	{
		Bukkit.getScheduler().runTaskAsynchronously(DynamicMazePlugin.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				Iterator<BlockVector> it = new BlockIterator();
				while(it.hasNext())
				{
					BlockVector vector = it.next();
					mUpdater.setBlock(mWorld, vector, mType.clone());
				}
				
				mUpdater.setCompletionCallback(new Callback()
				{
					@Override
					public void onFailure( Throwable exception )
					{
						setFailed(exception);
					}
					
					@Override
					public void onComplete()
					{
						setCompleted();
					}
				});
				
				mUpdater.execute();
			}
		});
	}
	
	private class BlockIterator implements Iterator<BlockVector>
	{
		private BlockVector mCurrent;
		
		public BlockIterator()
		{
			mCurrent = mMin.clone();
		}
		
		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
		
		@Override
		public BlockVector next()
		{
			if(!hasNext())
				throw new NoSuchElementException();
			
			BlockVector vec = mCurrent.clone();
			
			mCurrent.setX(mCurrent.getBlockX() + 1);
			
			if(mCurrent.getX() >= mMax.getBlockX())
			{
				mCurrent.setX(mMin.getBlockX());
				mCurrent.setZ(mCurrent.getBlockZ() + 1);
				
				if(mCurrent.getZ() >= mMax.getBlockZ())
				{
					mCurrent.setZ(mMin.getBlockZ());
					mCurrent.setY(mCurrent.getBlockY() + 1);
				}
			}
			
			return vec;
		}
		
		@Override
		public boolean hasNext()
		{
			return mCurrent.getBlockY() < mMax.getBlockY();
		}
	}
}
