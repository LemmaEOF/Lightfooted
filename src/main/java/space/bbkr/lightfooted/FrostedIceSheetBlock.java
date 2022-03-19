package space.bbkr.lightfooted;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FrostedIceSheetBlock extends IceBlock implements Waterloggable {
	public static final IntProperty AGE = Properties.AGE_3;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public static final VoxelShape SHAPE = Block.createCuboidShape(0, 13, 0, 16, 16, 16);


	public FrostedIceSheetBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(WATERLOGGED, true));

	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(AGE, WATERLOGGED);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.scheduledTick(state, world, pos, random);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if ((random.nextInt(3) == 0 || this.canMelt(world, pos, 4))
				&& world.getLightLevel(pos) > 11 - state.get(AGE) - state.getOpacity(world, pos)
				&& this.increaseAge(state, world, pos)) {
			BlockPos.Mutable mutable = new BlockPos.Mutable();

			for(Direction direction : Direction.values()) {
				mutable.set(pos, direction);
				BlockState blockState = world.getBlockState(mutable);
				if (blockState.isOf(this) && !this.increaseAge(blockState, world, mutable)) {
					world.scheduleBlockTick(mutable, this, MathHelper.nextInt(random, 20, 40));
				}
			}

		} else {
			world.scheduleBlockTick(pos, this, MathHelper.nextInt(random, 20, 40));
		}
	}

	private boolean increaseAge(BlockState state, World world, BlockPos pos) {
		int i = state.get(AGE);
		if (i < 3) {
			world.setBlockState(pos, state.with(AGE, i + 1), 2);
			return false;
		} else {
			this.melt(state, world, pos);
			return true;
		}
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (block.getDefaultState().isOf(this) && this.canMelt(world, pos, 2)) {
			this.melt(state, world, pos);
		}

		super.neighborUpdate(state, world, pos, block, fromPos, notify);
	}

	private boolean canMelt(BlockView world, BlockPos pos, int maxNeighbors) {
		int i = 0;
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for(Direction direction : Direction.values()) {
			mutable.set(pos, direction);
			if (world.getBlockState(mutable).isOf(this)) {
				++i;
				if (i >= maxNeighbors) {
					return false;
				}
			}
		}

		return true;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
}
