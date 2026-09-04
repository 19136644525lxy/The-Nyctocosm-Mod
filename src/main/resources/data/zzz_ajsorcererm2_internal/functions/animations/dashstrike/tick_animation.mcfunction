scoreboard players add @s aj.ajsorcererm2.animation.dashstrike.local_anim_time 1
scoreboard players operation @s aj.anim_time = @s aj.ajsorcererm2.animation.dashstrike.local_anim_time
function zzz_ajsorcererm2_internal:animations/dashstrike/apply_frame_as_root
execute if score @s aj.ajsorcererm2.animation.dashstrike.local_anim_time matches 70.. run function zzz_ajsorcererm2_internal:animations/dashstrike/end