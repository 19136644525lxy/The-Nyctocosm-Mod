scoreboard players operation @s aj.anim_time = @s aj.animation.idle.local_anim_time
function zzz_death_walker_internal:animations/idle/apply_frame_as_root
scoreboard players add @s aj.animation.idle.local_anim_time 1
execute if score @s aj.animation.idle.local_anim_time matches 60.. run function zzz_death_walker_internal:animations/idle/end