scoreboard players set @s aj.animation.walk.local_anim_time 0
tag @s remove aj.death_walker.animation.walk
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_death_walker_internal:animations/walk/tree/leaf_0