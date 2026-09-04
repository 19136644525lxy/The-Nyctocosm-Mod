scoreboard players operation @s aj.anim_time = @s aj.animation.slam.local_anim_time
function zzz_death_walker_internal:animations/slam/apply_frame_as_root
scoreboard players add @s aj.animation.slam.local_anim_time 1
execute if score @s aj.animation.slam.local_anim_time matches 90.. run function zzz_death_walker_internal:animations/slam/end