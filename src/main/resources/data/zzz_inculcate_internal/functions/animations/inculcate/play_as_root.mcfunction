scoreboard players set @s aj.animation.inculcate.local_anim_time 0
scoreboard players set @s aj.loop_mode 2
scoreboard players operation @s aj.anim_time = @s aj.animation.inculcate.local_anim_time
execute on passengers run data modify entity @s interpolation_duration set value 0
function zzz_inculcate_internal:animations/inculcate/tree/leaf_0
execute on passengers store result entity @s interpolation_duration int 1 run scoreboard players get $aj.default_interpolation_duration aj.i
tag @s add aj.inculcate.animation.inculcate