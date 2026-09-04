scoreboard players add @s aj.ajsorcerer.animation.initial_spawn.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajsorcerer.animation.initial_spawn.local_anim_time
function zzz_ajsorcerer_internal:animations/initial_spawn/apply_frame_as_root
execute if score @s aj.ajsorcerer.animation.initial_spawn.local_anim_time matches 331.. run function zzz_ajsorcerer_internal:animations/initial_spawn/end