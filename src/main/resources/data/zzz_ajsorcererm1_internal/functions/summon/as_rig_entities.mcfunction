scoreboard players operation @s aj.id = .aj.last_id aj.id
tag @s remove aj.new
function #ajsorcererm1:on_summon_as_rig_entities
execute if entity @s[tag=aj.ajsorcererm1.bone] run function zzz_ajsorcererm1_internal:summon/as_bones
execute if entity @s[tag=aj.ajsorcererm1.locator] run function zzz_ajsorcererm1_internal:summon/as_locators
execute if entity @s[tag=aj.ajsorcererm1.camera] run function zzz_ajsorcererm1_internal:summon/as_cameras