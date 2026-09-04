scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #ajsorcererm2:on_summon_as_rig_entities
execute if entity @s[tag=aj.ajsorcererm2.bone] run function zzz_ajsorcererm2_internal:summon/as_bones
execute if entity @s[tag=aj.ajsorcererm2.locator] run function zzz_ajsorcererm2_internal:summon/as_locators
execute if entity @s[tag=aj.ajsorcererm2.camera] run function zzz_ajsorcererm2_internal:summon/as_cameras