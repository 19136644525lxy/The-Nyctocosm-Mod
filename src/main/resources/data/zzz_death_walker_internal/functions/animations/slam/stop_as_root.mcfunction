scoreboard players set @s aj.animation.slam.local_anim_time 0
tag @s remove aj.death_walker.animation.slam
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_death_walker_internal:animations/slam/tree/leaf_0